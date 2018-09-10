package byashad.qoutespicture.picture.quotes.picturequotes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Transition;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Random;

public class adapteritem extends RecyclerView.Adapter<adapteritem.exampleviewholder> {
    private ArrayList<String> imageurl;
    private Context mcontext;

    private Onitemclicklistner mlistner;


    private ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#9ACCCD")),
                    new ColorDrawable(Color.parseColor("#8FD8A0")),
                    new ColorDrawable(Color.parseColor("#CBD890")),
                    new ColorDrawable(Color.parseColor("#DACC8F")),
                    new ColorDrawable(Color.parseColor("#D9A790")),
                    new ColorDrawable(Color.parseColor("#D18FD9")),
                    new ColorDrawable(Color.parseColor("#35013f")),
                    new ColorDrawable(Color.parseColor("#b643cd")),
                    new ColorDrawable(Color.parseColor("#ff5da2")),
                    new ColorDrawable(Color.parseColor("#99ddcc")),
                    new ColorDrawable(Color.parseColor("#2c3e50")),
                    new ColorDrawable(Color.parseColor("#D2D7D3")),
                    new ColorDrawable(Color.parseColor("#f1c40f")),
                    new ColorDrawable(Color.parseColor("#d35400")),
                    new ColorDrawable(Color.parseColor("#FF6772")),
                    new ColorDrawable(Color.parseColor("#DDFB5C"))
            };

    public ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

    public interface Onitemclicklistner
    {
        void onitemclick(int position);
    }

    public void setonitemclicklistner(Onitemclicklistner listner)
    {
        mlistner=listner;
    }

    public adapteritem(ArrayList<String> arrayList, Context mcontext) {
        this.imageurl = arrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public exampleviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.sampleitem,parent,false);


        return new exampleviewholder(v);
    }

    //    @NonNull
    //    @Override
    //    public exampleviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    //        View view= LayoutInflater.from(mcontext).inflate(R.layout.items,parent,false);
    //        return  new exampleviewholder(view);
    //    }


    @Override
    public void onBindViewHolder(@NonNull final exampleviewholder holder, int position) {

        //  exampleitem currentitem=arrayList.get(position);
        //        String imageurl=currentitem.getImageurl();
        //        String creator=currentitem.getMcreator();
        //        int likes=currentitem.getLikes();
        //
        //        holder.mTextviewcreator.setText(creator);
        //        holder.mtextviewlikes.setText(likes);
        //        Picasso.with(mcontext).load(imageurl).fit().centerInside().into(holder.mimageview);
        //    }
        String  currentimageurl=imageurl.get(position);
//       String urlofimg=currentitem.getImageurl();
//       String type=currentitem.getType();

        holder.imagestaus.requestLayout();




        Glide.with(mcontext)
                .load(currentimageurl).asBitmap()
//                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)

                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {

                        int w = resource.getWidth();
                        int h = resource.getHeight();

                        holder.imagestaus.setMaxHeight(h);
                        holder.imagestaus.setMaxWidth(w);
                        Log.i("lookatthis","height"+h);
                        Log.i("lookatthis","weidth"+w);
                        holder.imagestaus.setImageBitmap(resource);
                        return false;
                    }
                })
                .placeholder(getRandomDrawbleColor())


                .into(holder.imagestaus);



//        Picasso.with(mcontext).load(currentimageurl).fit()
//
//                .into(holder.imagestaus);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return imageurl.size();
    }



    public class exampleviewholder extends  RecyclerView.ViewHolder
    {


        public ImageView imagestaus;

        public exampleviewholder(View itemview)

        {

            super(itemview);
            imagestaus=itemview.findViewById(R.id.imageshow);

            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mlistner!=null)
                    {
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            mlistner.onitemclick(position);
                        }
                    }
                }
            });


        }
    }

}