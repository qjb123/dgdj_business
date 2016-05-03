package net.ezcx.dongguandaojia.business.view;

import android.util.SparseArray;

public class TabLayout {
    private SparseArray<TabButton> tabButtons = new SparseArray<TabButton>();
    private int selectedBackgroundResId;
    private int backgroundResId;
    private TabButton selectedButton;

    /**
     * @param backgroundResId         默认背景
     * @param selectedBackgroundResId 选中背景
     */
    public TabLayout(int backgroundResId, int selectedBackgroundResId) {
        this.selectedBackgroundResId = selectedBackgroundResId;
        this.backgroundResId = backgroundResId;
    }

    /**
     * @param buttons
     */
    public TabLayout addBtn(TabButton... buttons) {
        for (TabButton button : buttons) {
            button.setBackgroundResId(backgroundResId);
            button.setSelectedBackgroundResId(selectedBackgroundResId);
            tabButtons.put(button.getId(), button);
        }
        return this;
    }

    /**
     * @param btnId
     */
    public TabLayout selectBtn(int btnId) {
        selectBtn(btnId, null);
        return this;
    }

    /**
     * @param btnId
     */
    public void selectBtn(int btnId, OnSelectedListener selectedListener) {
        selectedButton = tabButtons.get(btnId);
        selectedButton.setSelect(true);
        for (int i = 0; i < tabButtons.size(); i++) {
            int tabKey = tabButtons.keyAt(i);
            if (tabKey != btnId) {
                tabButtons.get(tabKey).setSelect(false);
            } else {
                tabButtons.get(tabKey).setSelect(true);
            }
        }
        if (selectedListener != null) {
            selectedListener.onSelected();
        }
    }

    /**
     * 选择移动变化
     *
     * @param indexId
     * @param nextId
     * @param positionOffset
     */
    public void selectBtnOnScrolled(int indexId, int position, float positionOffset) {
        if (indexId == 0) {
            position = 1;
        } else if (indexId == 3) {
            position = 2;
        } else if (position == indexId) {
            //向右
            position = indexId + 1;
        } else {
            //向右
        }
        TabButton indexButton = tabButtons.valueAt(indexId);
        TabButton nextButton = tabButtons.valueAt(position);
        indexButton.getLlBelow().setAlpha(positionOffset);
        indexButton.getLlAbove().setAlpha(1 - positionOffset);
        nextButton.getLlBelow().setAlpha(1 - positionOffset);
        nextButton.getLlAbove().setAlpha(positionOffset);
        indexButton.postInvalidate();
    }


    public interface OnSelectedListener {
        void onSelected();
    }

    public TabButton getSelectedButton() {
        return selectedButton;
    }

}
