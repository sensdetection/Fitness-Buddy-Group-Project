package com.strengthcoach.strengthcoach.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;
import com.strengthcoach.strengthcoach.R;
import com.strengthcoach.strengthcoach.activities.TrainerDetailsActivity;
import com.strengthcoach.strengthcoach.models.Trainer;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrainerListAdapter extends RecyclerView.Adapter<TrainerListAdapter.TrainerViewHolder> {
    private LayoutInflater inflater;
    List<Trainer> trainers;
    Context context;

    public TrainerListAdapter(Context context, List<Trainer> trainers) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.trainers = trainers;
    }

    @Override
    public TrainerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trainer_list_row, parent, false);
        // Create the viewholder obj
        TrainerViewHolder holder = new TrainerViewHolder(view, new TrainerViewHolder.IMyViewHolderClicks() {

            @Override
            public void click(View caller) {
                // Do something
                Log.d("DEBUG", "LAUNCHING DETAILS ACTIVITY");
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(TrainerViewHolder holder, int position) {
        final Trainer trainer = trainers.get(position);

        // Initialize ViewPager adapter
        holder.mTrainerListPagerAdapter = new TrainerListPagerAdapter(context, trainer);
        holder.mViewPager.setAdapter(holder.mTrainerListPagerAdapter);

        // Set the profile image
        holder.ivProfileImage.setImageResource(0);
        Picasso.with(context).load(trainer.getProfileImageUrl()).into(holder.ivProfileImage);

        holder.tvTrainerName.setText(trainer.getName());
        holder.tvPrice.setText(trainer.getPriceFormatted());
        holder.tvAboutMe.setText(trainer.getAboutMe());
//        setGymNameAndCity(holder, trainer);
        holder.ratingBar.setRating((float) trainer.getRatings());
        Drawable progress = holder.ratingBar.getProgressDrawable();
        DrawableCompat.setTint(progress, context.getResources().getColor(R.color.amber));

        setNumReviews(holder, trainer);
        // animate(holder);
    }

    // DO NOT REMOVE: This will be used later for experimentation with animation
    private void animate(TrainerViewHolder holder) {
        YoYo.with(Techniques.Hinge)
                .duration(2000)
                .playOn(holder.itemView);
    }

    private void setNumReviews(TrainerViewHolder holder, Trainer trainer) {
        final TextView tvNumReviews = holder.tvNumReviews;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Review");
        query.whereEqualTo("reviewee", trainer.getObjectId());
        query.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    Log.d("DEBUG", "Number of reviews: " + count);
                    if (count == 1) {
                        tvNumReviews.setText(count + " Review");
                    } else {
                        tvNumReviews.setText(count + " Reviews");
                    }
                } else {
                    Log.d("DEBUG", "Failed to get number of reviews");
                }
            }
        });
    }

//    private void setGymNameAndCity(TrainerViewHolder holder, Trainer trainer) {
//        final TextView tvGymName = holder.tvGymName;
//        final TextView tvCity = holder.tvCity;
//        ParseQuery<Gym> query = ParseQuery.getQuery("Gym");
//        query.whereEqualTo("trainers", trainer);
//        query.include("address");
//        query.findInBackground(new FindCallback<Gym>() {
//            public void done(List<Gym> gyms, ParseException e) {
//                if (e == null) {
//                    Log.d("DEBUG", "Retrieved " + gyms.size() + " Gym");
//                    Gym gym = gyms.get(0);
//                    tvGymName.setText(gym.getName());
//                    tvCity.setText(gym.getAddress().getCity());
//                } else {
//                    Log.d("DEBUG", "Failed to fetch Gym info. Error: " + e.getMessage());
//                }
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return trainers.size();
    }

    // Custom ViewHolder
    static class TrainerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivImage;
        CircleImageView ivProfileImage;
        TextView tvPrice;
        TextView tvTrainerName;
        TextView tvAboutMe;
        RatingBar ratingBar;
        TextView tvNumReviews;
        public IMyViewHolderClicks mListener;
        private final Context context;
        TrainerListPagerAdapter mTrainerListPagerAdapter;
        ViewPager mViewPager;

        public TrainerViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);

            // Get reference to context
            context = itemView.getContext();

            // Get the reference to views
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            ivProfileImage = (CircleImageView) itemView.findViewById(R.id.ivProfileImage);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvTrainerName = (TextView) itemView.findViewById(R.id.tvTrainerName);
            tvAboutMe = (TextView) itemView.findViewById(R.id.tvAboutMe);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            tvNumReviews = (TextView) itemView.findViewById(R.id.tvNumReviews);
            mListener = listener;
            itemView.setOnClickListener(this);
            // Get the reference to viewpager
            mViewPager = (ViewPager) itemView.findViewById(R.id.pager);
        }

        @Override
        public void onClick(View v) {
            // View specific clicks will be handled here eg. click on the favorite icon
            //  mListener.click(v);
            // Launch Trainer details activity
            final Intent intent;
            intent =  new Intent(context, TrainerDetailsActivity.class);
            context.startActivity(intent);
        }

        public static interface IMyViewHolderClicks {
            public void click(View caller);
        }
    }
}
