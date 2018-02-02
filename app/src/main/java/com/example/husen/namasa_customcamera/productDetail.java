package com.example.husen.namasa_customcamera;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class productDetail extends AppCompatActivity {

    private ViewPager itemPic;
    private LinearLayout sliderDots;
    private int dots_num;
    private ImageView[] dots;
    private List<Integer> picImages= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        picImages.add(R.drawable.ic_launcher_background);
        picImages.add(R.drawable.ic_launcher_foreground);

        itemPic = (ViewPager) findViewById(R.id.itemPic);
        sliderDots = (LinearLayout) findViewById(R.id.sliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, picImages);
        itemPic.setAdapter(viewPagerAdapter);

        //dot
        dots_num = viewPagerAdapter.getCount();
        dots = new ImageView[dots_num];

        //buat dot sejumlah banyak gambar
        for(int i = 0; i < dots_num; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDots.addView(dots[i], params);
        }
        //set dot pertama active
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        itemPic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< dots_num; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    public class ViewPagerAdapter extends PagerAdapter{

        private Context context;
        private LayoutInflater layoutInflater;
//        private Integer[] images = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground};
        private List<Integer> images = new ArrayList<>();

        public ViewPagerAdapter(Context context, List<Integer> images){
            this.context = context;
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.product_image_viewpager, null);
            ImageView pic = (ImageView) view.findViewById(R.id.imageProduct);
            Log.d("position", String.valueOf(position));
            pic.setImageResource(images.get(position));

            ViewPager vp = (ViewPager) container;
            vp.addView(view, 0);
            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ViewPager vp = (ViewPager) container;
            View view = (View) object;
            vp.removeView(view);

        }
    }

}
