package com.akul.inspire;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>
{
    Context mCtx;
    List<Quote> quoteList;

    public QuoteAdapter(Context mCtx, List<Quote> quoteList)
    {
        this.mCtx = mCtx;
        this.quoteList = quoteList;
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.layout_post,
                viewGroup, false);
        QuoteViewHolder quoteViewHolder = new QuoteViewHolder(view);
        return quoteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final QuoteViewHolder quoteViewHolder, int i) {
        final Quote quote = quoteList.get(i);



        quoteViewHolder.postText.setText(quote.getQuote());
        quoteViewHolder.postCat.setText("("+quote.getQcategory()+")");
        quoteViewHolder.postAuthor.setText(quote.getQauthor());

    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }




    class QuoteViewHolder extends RecyclerView.ViewHolder {

        TextView postText, postCat, postAuthor;

        public QuoteViewHolder(View itemView) {
            super(itemView);

            postText = itemView.findViewById(R.id.pQuote);
            postCat = itemView.findViewById(R.id.pCategory);
            postAuthor = itemView.findViewById(R.id.pAuthor);

        }
    }

}
