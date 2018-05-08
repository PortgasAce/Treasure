package com.portgas.treasure;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.portgas.treasure.portgastreasure.R;

public class GlideActivity extends Activity {

  private static final String IMAGE_URL = "http://ow0ekshxd.bkt.clouddn.com/zoomRecyclerView1.png";

  private ImageView imageView;
  private TextView textView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_glide);

    imageView = findViewById(R.id.iv);
    textView = findViewById(R.id.tv);
    imageView.setVisibility(View.INVISIBLE);

    //Glide.with(this).load(IMAGE_URL).into(imageView);

    Glide.with(this)
        .load(IMAGE_URL)
        .listener(new RequestListener<Drawable>() {
          @Override
          public boolean onLoadFailed(@Nullable GlideException e, Object model,
              Target<Drawable> target,
              boolean isFirstResource) {
            return false;
          }

          @Override
          public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
              DataSource dataSource, boolean isFirstResource) {
            //imageView.setImageDrawable(resource);
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            return false; //返回false 不设置图片，可以保留动画变换
          }
        }).into(imageView);
  }

}