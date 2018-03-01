package com.example.husen.namasa_customcamera;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class productDetail extends AppCompatActivity {

    private ViewPager itemPic;
    private LinearLayout sliderDots;
    private int dots_num;
    private ImageView[] dots;
    private List<Integer> picImages= new ArrayList<>();
    private Toolbar toolbar;
    private ImageButton leftNav, rightNav;

    //untuk recycler
    private List<Product> productList = new ArrayList<>();
    private RecyclerView rview;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        picImages.add(R.drawable.ic_launcher_background);
        picImages.add(R.drawable.ic_launcher_foreground);

        itemPic = (ViewPager) findViewById(R.id.itemPic);
        sliderDots = (LinearLayout) findViewById(R.id.sliderDots);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
            }
        });

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

        leftNav = (ImageButton) findViewById(R.id.left_nav);
        rightNav = (ImageButton) findViewById(R.id.right_nav);

        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = itemPic.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    itemPic.setCurrentItem(tab);
                } else if (tab == 0) {
                    itemPic.setCurrentItem(tab);
                }
            }
        });

        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = itemPic.getCurrentItem();
                tab++;
                itemPic.setCurrentItem(tab);
            }
        });

        //set adapter
        rview = findViewById(R.id.recyclerViewProduct);
        rview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        rview.setLayoutManager(layoutManager);
        rview.setItemAnimator(new DefaultItemAnimator());
        adapter = new ProductAdapter(productList);
        rview.setAdapter(adapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        prepareProduct();


    }

    private void prepareProduct() {
        productList.add(new Product("husen ganteng", 7000,"asd"));
        productList.add(new Product("husen ganteng", 7000,"asd"));
        productList.add(new Product("husen ganteng", 7000,"asd"));

        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_overflow_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.report_item){
            AlertDialog.Builder builder = new AlertDialog.Builder(productDetail.this);
            View view = getLayoutInflater().inflate(R.layout.dialog_report_product, null);

            Button no = view.findViewById(R.id.dialog_no);
            Button yes = view.findViewById(R.id.dialog_yes);

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(productDetail.this, "no clicked", Toast.LENGTH_SHORT).show();

                }
            });

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            builder.setView(view);
            builder.show();
        }

        return true;
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

    public class ProductAdapter extends RecyclerView.Adapter<productDetail.ProductAdapter.ProductViewHolder> {

        public List<Product> listProduct;

        public ProductAdapter(List<Product> listProduct){
            this.listProduct = listProduct;
        }

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_horizontal, parent, false);
            return new ProductAdapter.ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            Product p= listProduct.get(position);
//            holder.productImage.setImageResource();
            holder.productTitle.setText(p.getTitle());
            holder.productPrice.setText(String.valueOf(p.getHarga()));
            holder.sellerTitle.setText(p.getPenjualName());
        }

        @Override
        public int getItemCount() {
            return listProduct.size();
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder{
            public ImageView productImage, sellerImage;
            public TextView productTitle, productPrice, sellerTitle;

            public ProductViewHolder(View itemView) {
                super(itemView);
                productImage = itemView.findViewById(R.id.listProductImage);
                sellerImage = itemView.findViewById(R.id.listProductSellerImage);
                productTitle = itemView.findViewById(R.id.listProductTitle);
                productPrice = itemView.findViewById(R.id.listProductPrice);
                sellerTitle = itemView.findViewById(R.id.listProductSellerName);
            }
        }

    }

}
