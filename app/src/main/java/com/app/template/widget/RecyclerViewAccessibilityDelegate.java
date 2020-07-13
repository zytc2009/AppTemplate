package com.app.template.widget;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.NonNull;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * The AccessibilityDelegate used by RecyclerView.
 * <p>
 * This class handles basic accessibility actions and delegates them to LayoutManager.
 */
public class RecyclerViewAccessibilityDelegate extends AccessibilityDelegateCompat {
    final RecyclerView mRecyclerView;
    final RecyclerViewAccessibilityDelegate.ItemDelegate mItemDelegate;


    public RecyclerViewAccessibilityDelegate(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mItemDelegate = new RecyclerViewAccessibilityDelegate.ItemDelegate(this);
    }

    boolean shouldIgnore() {
        return mRecyclerView.hasPendingAdapterUpdates();
    }

    @Override
    public boolean performAccessibilityAction(View host, int action, Bundle args) {
        if (super.performAccessibilityAction(host, action, args)) {
            return true;
        }
        if (!shouldIgnore() && mRecyclerView.getLayoutManager() != null) {
            return mRecyclerView.getLayoutManager().performAccessibilityAction(action, args);
        }

        return false;
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        if (!shouldIgnore() && mRecyclerView.getLayoutManager() != null) {
            mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfo(info);
        }
    }

    @Override
    public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(host, event);
        if (host instanceof RecyclerView && !shouldIgnore()) {
            RecyclerView rv = (RecyclerView) host;
            if (rv.getLayoutManager() != null) {
                rv.getLayoutManager().onInitializeAccessibilityEvent(event);
            }
        }
    }

    /**
     * Gets the AccessibilityDelegate for an individual item in the RecyclerView.
     * A basic item delegate is provided by default, but you can override this
     * method to provide a custom per-item delegate.
     */
    @NonNull
    public AccessibilityDelegateCompat getItemDelegate() {
        return mItemDelegate;
    }

    /**
     * The default implementation of accessibility delegate for the individual items of the
     * RecyclerView.
     * <p>
     * If you are overriding {@code RecyclerViewAccessibilityDelegate#getItemDelegate()} but still
     * want to keep some default behavior, you can create an instance of this class and delegate to
     * the parent as necessary.
     */
    public static class ItemDelegate extends AccessibilityDelegateCompat {
        final RecyclerViewAccessibilityDelegate mRecyclerViewDelegate;
        private Map<View, AccessibilityDelegateCompat> mOriginalItemDelegates = new WeakHashMap<>();

        /**
         * Creates an item delegate for the given {@code RecyclerViewAccessibilityDelegate}.
         *
         * @param recyclerViewDelegate The parent RecyclerView's accessibility delegate.
         */
        public ItemDelegate(@NonNull RecyclerViewAccessibilityDelegate recyclerViewDelegate) {
            mRecyclerViewDelegate = recyclerViewDelegate;
        }

        /**
         * Saves a reference to the original delegate of the itemView so that it's behavior can be
         * combined with the ItemDelegate's behavior.
         */
        void saveOriginalDelegate(View itemView) {
            AccessibilityDelegateCompat delegate = ViewCompat.getAccessibilityDelegate(itemView);
            if (delegate != null && delegate != this) {
                mOriginalItemDelegates.put(itemView, delegate);
            }
        }

        /**
         * @return The delegate associated with itemView before the view was bound.
         */
        AccessibilityDelegateCompat getAndRemoveOriginalDelegateForItem(View itemView) {
            return mOriginalItemDelegates.remove(itemView);
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            if (!mRecyclerViewDelegate.shouldIgnore()
                    && mRecyclerViewDelegate.mRecyclerView.getLayoutManager() != null) {
                mRecyclerViewDelegate.mRecyclerView.getLayoutManager()
                        .onInitializeAccessibilityNodeInfoForItem(host, info);
                AccessibilityDelegateCompat originalDelegate = mOriginalItemDelegates.get(host);
                if (originalDelegate != null) {
                    originalDelegate.onInitializeAccessibilityNodeInfo(host, info);
                }
            }
        }

        @Override
        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            if (super.performAccessibilityAction(host, action, args)) {
                return true;
            }
            if (!mRecyclerViewDelegate.shouldIgnore()
                    && mRecyclerViewDelegate.mRecyclerView.getLayoutManager() != null) {
                AccessibilityDelegateCompat originalDelegate = mOriginalItemDelegates.get(host);
                if (originalDelegate != null
                        && originalDelegate.performAccessibilityAction(host, action, args)) {
                    return true;
                } else {
                    return mRecyclerViewDelegate.mRecyclerView.getLayoutManager()
                            .performAccessibilityActionForItem(host, action, args);
                }
            }
            return false;
        }
    }
}
