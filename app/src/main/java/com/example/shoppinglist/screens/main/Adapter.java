package com.example.shoppinglist.screens.main;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.shoppinglist.App;
import com.example.shoppinglist.R;
import com.example.shoppinglist.model.Purchase;
import com.example.shoppinglist.screens.details.PurchaseDetailsActivity;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.PurchaseViewHolder> {

    private SortedList<Purchase> sortedList;

    public Adapter() {
        sortedList = new SortedList<>(Purchase.class, new SortedList.Callback<Purchase>() {
            @Override
            public int compare(Purchase o1, Purchase o2) {
                if (!o2.done && o1.done) {
                    return 1;
                }
                if (o2.done && !o1.done) {
                    return -1;
                }
                return (int) (o2.timestamp - o1.timestamp);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Purchase oldItem, Purchase newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Purchase item1, Purchase item2) {
                return item1.uid == item2.uid;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PurchaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Purchase> purchases) {
        sortedList.replaceAll(purchases);
    }

    static class PurchaseViewHolder extends RecyclerView.ViewHolder {

        TextView purchaseText;
        CheckBox completed;
        View delete;

        Purchase purchase;

        boolean silentUpdate;

        public PurchaseViewHolder(@NonNull View itemView) {
            super(itemView);

            purchaseText = itemView.findViewById(R.id.purchase_text);
            completed = itemView.findViewById(R.id.completed);
            delete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PurchaseDetailsActivity.start((Activity) itemView.getContext(), purchase);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.getInstance().getPurchaseDao().delete(purchase);
                }
            });

            completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (!silentUpdate) {
                        purchase.done = checked;
                        App.getInstance().getPurchaseDao().update(purchase);
                    }
                    updateStrokeOut();
                }
            });
        }

        public void bind(Purchase purchase) {
            this.purchase = purchase;

            purchaseText.setText(purchase.text);
            updateStrokeOut();

            silentUpdate = true;
            completed.setChecked(purchase.done);
            silentUpdate = false;
        }

        private void updateStrokeOut() {
            if (purchase.done) {
                purchaseText.setPaintFlags(purchaseText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                purchaseText.setPaintFlags(purchaseText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}
