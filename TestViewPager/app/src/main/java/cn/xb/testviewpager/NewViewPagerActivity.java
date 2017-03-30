package cn.xb.testviewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 席彪彪 on 2017/3/29.
 */

public class NewViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    ViewPager varietyViewpPager;
    ArrayList<View> vpagerlistView = new ArrayList<View>();
    ImageView backgroundIv;
    //    One two three four five
    public static final int[] viewColorsOne = {100, 100, 200};
    public static final int[] viewColorsTwo = {155, 44, 66};
    public static final int[] viewColorsThree = {77, 185, 185};
    public static final int[] viewColorsFour = {185, 168, 95};
    public static final int[] viewColorsFive = {185, 99, 73};

    public static int firstColor = 0;
    public static int secondColor = 0;
    public static int thirdColor = 0;

    public static int firstColorTo = 0;
    public static int secondColorTo = 0;
    public static int thirdColorTo = 0;

    public static int page = 0;
    public static int pageTo = 0;
    public static boolean CHANGE_TYPE = true;//滑动的位置相对于当前页面是向左还是向右 true为右 也就是 page0-page1
    public static float beforeChange = 0;
    public static float changeNum = 0f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_viewpager_layout);
        page = 0;
        pageTo = 0;
        firstShow=true;
        CHANGE_TYPE = true;
        for (int i = 0; i < 5; i++) {
            TextView textView = new TextView(this);
            textView.setText("view" + i);
            textView.setBackgroundColor(0);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(layoutParams);
            textView.setTextColor(Color.BLUE);
            textView.setGravity(Gravity.CENTER);
            vpagerlistView.add(textView);
        }
        backgroundIv = (ImageView) findViewById(R.id.iv_background);
        varietyViewpPager = (ViewPager) findViewById(R.id.vp_variety);
        varietyViewpPager.setOnPageChangeListener(this);
        varietyViewpPager.setAdapter(new MyPagerAdapter());
        varietyViewpPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
//        setColor(page);
        setBackground(viewColorsOne[0], viewColorsOne[1], viewColorsOne[2]);
    }


    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            if (firstColor == 0 && page == 0) {
//                setColor(page);
//            }
//            if (page == 0 && firstColorTo == 0.0f) {
//                setColorTo(page + 1);
//            }
            if(changeNum!=0.0f&&CHANGE_TYPE){
                setBackground((int) (firstColor + beforeChange * (firstColorTo - firstColor)), (int) (secondColor + beforeChange * (secondColorTo - secondColor)), (int) (thirdColor + beforeChange * (thirdColorTo - thirdColor)));
                Log.e("handleMessage", CHANGE_TYPE+"handleMessage:firstColor ++"+firstColor+"+++firstColorTo+++"+firstColorTo );
            }else  if(changeNum!=0.0f&&!CHANGE_TYPE){
                setBackground((int) (firstColor +(1f-beforeChange) * (firstColorTo - firstColor)), (int) (secondColor + (1f-beforeChange) * (secondColorTo - secondColor)), (int) (thirdColor + (1f-beforeChange) * (thirdColorTo - thirdColor)));
                Log.e("handleMessage", CHANGE_TYPE+"handleMessage:firstColor ++"+firstColor+"+++firstColorTo+++"+firstColorTo );

            }
//            else if (changeNum<0.0f){
//
//            }

            myHandler.sendEmptyMessageDelayed(0, 16);
        }
    };

    public class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return vpagerlistView.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(vpagerlistView.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(vpagerlistView.get(position));
            return vpagerlistView.get(position);
        }

    }

    public static boolean isRun = false;
    public static boolean firstShow=true;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        changeNum = positionOffset - beforeChange;


//        if(positionOffset==0){
//            setColor(position);
//        }
//        if (position == 0) {
//            setColorTo(position+1);//右滑
//            CHANGE_TYPE=true;
//        }else if(position==vpagerlistView.size()-1){
//            setColorTo(position);//左滑
//            CHANGE_TYPE=false;
//        }else if (changeNum > 0.5f) {
//            setColorTo(position);//左滑
//            CHANGE_TYPE=false;
//        } else if (changeNum < -0.5f) {
//            setColorTo(position+1);//右滑
//            CHANGE_TYPE=true;
//        }
        if(firstShow){
            setColor(page);
            firstShow=false;
        }
        if(positionOffset==0.0f&&page!=position){
            page=position;
            setColor(page);
            CHANGE_TYPE=true;
        }
        if(page ==pageTo){
            pageTo = page+1;
            setColorTo(pageTo);
            CHANGE_TYPE=true;
        }

        if (page == 0) {
            pageTo=page+1;
            setColorTo(pageTo);//右滑
            CHANGE_TYPE=true;
        }else if(page==vpagerlistView.size()-1){
            pageTo=page-1;
            setColorTo(pageTo);//左滑
            CHANGE_TYPE=false;
        }else if (changeNum > 0.9f) {
            pageTo=page-1;
            setColorTo(pageTo);//左滑
            CHANGE_TYPE=false;
        } else if (changeNum < -0.9f) {
            pageTo=page+1;
            setColorTo(pageTo);//右滑
            CHANGE_TYPE=true;
        }
        beforeChange = positionOffset;
        if (positionOffset == 0.0f) {
            myHandler.removeMessages(0);
            isRun = false;
        } else {
            if (!isRun) {
                myHandler.sendEmptyMessageDelayed(0, 16);
                isRun = true;
            }
        }
//                Log.e("onPageScrolled", "positionOffset===="+positionOffset+"------changeNum=="+changeNum);
//                Log.e("onPageScrolled", "positionOffset"+positionOffset+"------changeNum=="+changeNum);
//        Log.e("onPageScrolled", "changeNum+++"+changeNum+ "int position+++" + position + "positionOffset====" + positionOffset + "positionOffsetPixels===" + positionOffsetPixels);
//        Log.e("onPageScrolled", "onPageScrolled: +"+ CHANGE_TYPE);
        Log.e("onPageScrolled", "page++"+page+"pageTo+++"+pageTo+"+++CHANGE_TYPE"+CHANGE_TYPE);
    }

    @Override
    public void onPageSelected(int position) {
//        page = position;
//        if(Scrollstate!=1){
//            setColor(position);
//        }
//        Log.e("onPageSelected", "onPageSelected: ===" + position+"++Scrollstate++"+Scrollstate);
    }

    public static int Scrollstate= 0;
    @Override
    public void onPageScrollStateChanged(int state) {
        Scrollstate=state;
        Log.e("onPageScrollStateChanged", "onPageScrollStateChanged: +++"+state );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        page = 0;
        varietyViewpPager.removeAllViews();
        vpagerlistView = null;
    }


    public void setColor(int pageNumber) {
        switch (pageNumber) {
            case 0:
                firstColor = viewColorsOne[0];
                secondColor = viewColorsOne[1];
                thirdColor = viewColorsOne[2];
                break;
            case 1:
                firstColor = viewColorsTwo[0];
                secondColor = viewColorsTwo[1];
                thirdColor = viewColorsTwo[2];
                break;
            case 2:
                firstColor = viewColorsThree[0];
                secondColor = viewColorsThree[1];
                thirdColor = viewColorsThree[2];
                break;
            case 3:
                firstColor = viewColorsFour[0];
                secondColor = viewColorsFour[1];
                thirdColor = viewColorsFour[2];
                break;
            case 4:
                firstColor = viewColorsFive[0];
                secondColor = viewColorsFive[1];
                thirdColor = viewColorsFive[2];
                break;

        }
        //设置背景色

    }

    public void setColorTo(int pageNumber) {
        switch (pageNumber) {
            case 0:
                firstColorTo = viewColorsOne[0];
                secondColorTo = viewColorsOne[1];
                thirdColorTo = viewColorsOne[2];
                break;
            case 1:
                firstColorTo = viewColorsTwo[0];
                secondColorTo = viewColorsTwo[1];
                thirdColorTo = viewColorsTwo[2];
                break;
            case 2:
                firstColorTo = viewColorsThree[0];
                secondColorTo = viewColorsThree[1];
                thirdColorTo = viewColorsThree[2];
                break;
            case 3:
                firstColorTo = viewColorsFour[0];
                secondColorTo = viewColorsFour[1];
                thirdColorTo = viewColorsFour[2];
                break;
            case 4:
                firstColorTo = viewColorsFive[0];
                secondColorTo = viewColorsFive[1];
                thirdColorTo = viewColorsFive[2];
                break;

        }
        //设置背景色
//        setBackground(firstColor, secondColor, thirdColor);
    }

    public void setBackground(int red, int green, int blue) {
//        backgroundIv.setBackgroundColor(Color.argb(0,a,b,c));
        backgroundIv.setBackgroundColor(Color.rgb(red, green, blue));
//        Log.e("setBackground", "red" + red + "+++green" + green + "+++blue" + blue);
    }
}
