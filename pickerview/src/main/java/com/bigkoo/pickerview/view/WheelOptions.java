package com.bigkoo.pickerview.view;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;
import com.bigkoo.pickerview.view.addressmodel.BaseModel;
import com.bigkoo.pickerview.view.addressmodel.CityModel;
import com.bigkoo.pickerview.view.addressmodel.ProvinceModel;

public class WheelOptions<T extends BaseModel> {
    private View view;
    private WheelView wv_option1;
    private WheelView wv_option2;
    private WheelView wv_option3;

    private boolean linkage = false;
    private OnItemSelectedListener wheelListener_option1;
    private OnItemSelectedListener wheelListener_option2;
    private List<ProvinceModel> provinceModelList;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public WheelOptions(View view) {
        super();
        this.view = view;
        setView(view);
    }

//	public void setPicker(ArrayList<T> optionsItems) {
//		setPicker(optionsItems, null, null, false);
//	}
//
//	public void setPicker(ArrayList<T> options1Items,
//			ArrayList<ArrayList<T>> options2Items, boolean linkage) {
//		setPicker(options1Items, options2Items, null, linkage);
//	}

    public void setPicker(final List<ProvinceModel> provinceModelList,
                          boolean linkage) {
        this.provinceModelList = provinceModelList;
        this.linkage = linkage;
        int len = ArrayWheelAdapter.DEFAULT_LENGTH;
        // 选项1
        wv_option1 = (WheelView) view.findViewById(R.id.options1);
        // 选项2
        wv_option2 = (WheelView) view.findViewById(R.id.options2);
        // 选项3
        wv_option3 = (WheelView) view.findViewById(R.id.options3);
        if (provinceModelList != null && provinceModelList.size() > 0) {
            wv_option1.setAdapter(new ArrayWheelAdapter(getStrList(provinceModelList), len));// 设置显示数据
            wv_option1.setCurrentItem(0);// 初始化时显示的数据
            if (provinceModelList.get(0).getCityList() != null && provinceModelList.get(0).getCityList().size() > 0) {
                wv_option2.setAdapter(new ArrayWheelAdapter(getStrList(provinceModelList.get(0).getCityList())));// 设置显示数据
                wv_option2.setCurrentItem(wv_option1.getCurrentItem());// 初始化时显示的数据
                if (provinceModelList.get(0).getCityList().get(0).getDistrictList() != null && provinceModelList.get(0).getCityList().get(0).getDistrictList().size() > 0) {
                    wv_option3.setAdapter(new ArrayWheelAdapter(getStrList(provinceModelList.get(0).getCityList().get(0).getDistrictList())));// 设置显示数据
                    wv_option3.setCurrentItem(wv_option3.getCurrentItem());// 初始化时显示的数据
                }
            }
        }

        int textSize = 22;
        wv_option1.setTextSize(textSize);
        wv_option2.setTextSize(textSize);
        wv_option3.setTextSize(textSize);

        // 联动监听器
        wheelListener_option1 = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                int opt2Select = wv_option2.getCurrentItem();//上一个opt2的选中位置
                //新opt2的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                opt2Select = opt2Select >= provinceModelList.get(index).getCityList().size() - 1 ? provinceModelList.get(index).getCityList().size() - 1 : opt2Select;

                wv_option2.setAdapter(new ArrayWheelAdapter(getStrList(provinceModelList.get(index).getCityList())));
                wv_option2.setCurrentItem(opt2Select);

                wheelListener_option2.onItemSelected(opt2Select);

            }
        };
        wheelListener_option2 = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                int opt3Select = wv_option3.getCurrentItem();//上一个opt3的选中位置
                //新opt3的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                opt3Select = opt3Select >=
                        provinceModelList.get(wv_option1.getCurrentItem()).getCityList().get(index).getDistrictList().size() - 1
                        ? provinceModelList.get(wv_option1.getCurrentItem()).getCityList().get(index).getDistrictList().size() - 1 : opt3Select;

                wv_option3.setAdapter(new ArrayWheelAdapter(getStrList(provinceModelList.get(wv_option1.getCurrentItem()).getCityList().get(index).getDistrictList())));
                wv_option3.setCurrentItem(opt3Select);
            }
        };

//		// 添加联动监听
        wv_option1.setOnItemSelectedListener(wheelListener_option1);
        wv_option2.setOnItemSelectedListener(wheelListener_option2);
    }

    private <T extends BaseModel> List<String> getStrList(List<T> models) {
        List<String> stringList = new ArrayList<>();
        for (BaseModel baseModel : models) {
            stringList.add(baseModel.getName());
        }
        return stringList;
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     * @param label3
     */
    public void setLabels(String label1, String label2, String label3) {
        if (label1 != null)
            wv_option1.setLabel(label1);
        if (label2 != null)
            wv_option2.setLabel(label2);
        if (label3 != null)
            wv_option3.setLabel(label3);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_option1.setCyclic(cyclic);
        wv_option2.setCyclic(cyclic);
        wv_option3.setCyclic(cyclic);
    }

    /**
     * 分别设置第一二三级是否循环滚动
     *
     * @param cyclic1,cyclic2,cyclic3
     */
    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        wv_option1.setCyclic(cyclic1);
        wv_option2.setCyclic(cyclic2);
        wv_option3.setCyclic(cyclic3);
    }

    /**
     * 设置第二级是否循环滚动
     *
     * @param cyclic
     */
    public void setOption2Cyclic(boolean cyclic) {
        wv_option2.setCyclic(cyclic);
    }

    /**
     * 设置第三级是否循环滚动
     *
     * @param cyclic
     */
    public void setOption3Cyclic(boolean cyclic) {
        wv_option3.setCyclic(cyclic);
    }

    /**
     * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2
     *
     * @return
     */
    public int[] getCurrentItems() {
        int[] currentItems = new int[3];
        currentItems[0] = wv_option1.getCurrentItem();
        currentItems[1] = wv_option2.getCurrentItem();
        currentItems[2] = wv_option3.getCurrentItem();
        return currentItems;
    }

    public void setCurrentItems(int option1, int option2, int option3) {
        if (linkage) {
            itemSelected(option1, option2, option3);
        }
        wv_option1.setCurrentItem(option1);
        wv_option2.setCurrentItem(option2);
        wv_option3.setCurrentItem(option3);
    }

    private void itemSelected(int opt1Select, int opt2Select, int opt3Select) {
        if (this.provinceModelList.get(opt1Select) != null) {
            wv_option2.setAdapter(new ArrayWheelAdapter(getStrList(this.provinceModelList.get(opt1Select).getCityList())));
            wv_option2.setCurrentItem(opt2Select);
        }
        if (this.provinceModelList.get(opt1Select).getCityList().get(opt2Select).getDistrictList() != null) {
            wv_option3.setAdapter(new ArrayWheelAdapter(getStrList(this.provinceModelList.get(opt1Select).getCityList().get(opt2Select).getDistrictList())));
            wv_option3.setCurrentItem(opt3Select);
        }
    }


}
