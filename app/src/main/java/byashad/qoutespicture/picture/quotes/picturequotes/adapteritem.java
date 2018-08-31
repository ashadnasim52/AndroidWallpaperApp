package byashad.qoutespicture.picture.quotes.picturequotes;

import android.content.Context;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class adapteritem extends RecyclerView.Adapter<adapteritem.exampleviewholder> {
    private ArrayList<String> imageurl;
    private Context mcontext;

    private Onitemclicklistner mlistner;


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
    public void onBindViewHolder(@NonNull exampleviewholder holder, int position) {

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
                .load(currentimageurl)

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