package com.example.examapp.ui.home.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.examapp.R;
import com.example.examapp.data.database.DatabaseHero;
import com.example.examapp.data.network.Hero;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeroesAdapter extends RecyclerView.Adapter<HeroesAdapter.HeroViewHolder> {

    private static final String TAG = "HeroesAdapter";
    private HeroesPresenter mHeroPresenter;
    private final listItemClickListener mOnClickListener;
    private static List<DatabaseHero> mHeroEntries;
    private Context mContext;
    private List<Hero> values;


    public interface listItemClickListener {
        void onListItemClick(int position);
    }

    public HeroesAdapter(listItemClickListener mOnClickListener, Context context) {
        mHeroPresenter = new HeroesPresenter(context);
        this.mOnClickListener = mOnClickListener;
        this.mContext = context;

    }

    public static List<DatabaseHero> getHeroList() {
        return mHeroEntries;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.single_view_item_recycler;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        HeroViewHolder heroViewHolder = new HeroViewHolder(view);
        return heroViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder heroViewHolder, int i) {
        mHeroPresenter.onBind(heroViewHolder, i,mHeroEntries,values);

    }
    public void setHeroEntries(List<DatabaseHero> taskEntries) {
        mHeroEntries = taskEntries;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mHeroPresenter.getViewCount();
    }

    public void getList(List<Hero> repos) {
        values = repos;
        Log.i(TAG, values.toString());
    }

    public class HeroViewHolder extends RecyclerView.ViewHolder implements HeroesMvpView, View.OnClickListener {
        @BindView(R.id.hero_name)
        TextView mHeroName;

        @BindView(R.id.hero_abilities)
        TextView mHeroAbilities;

        @BindView(R.id.hero_image)
        ImageView mHeroImage;

        @BindView(R.id.favorite_icon_image_view)
        ImageView mFavoriteView;

        public HeroViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mHeroImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHeroPresenter.imageToFull(mHeroEntries.get(getAdapterPosition()).getImageUrl());
                }
            });


        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }


    }
}
