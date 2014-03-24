//package com.closetshare.app;

/**
 * Created by dianaignacio on 3/23/14.

/*
 * Copyright (C) 2012 jfrankie (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.closetshare.app;
/*
 * Copyright (C) 2012 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

        import java.util.List;

        import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.TextView;
        import android.app.Activity;

public class ClosetAdapter extends ArrayAdapter<Item> {

    private List<Item> itemList;
    private Context context;
    private boolean useList = true;

    public ClosetAdapter(List<Item> itemList, Context ctx) {
        super(ctx, android.R.layout.simple_list_item_1, itemList);
        this.itemList = itemList;
        this.context = ctx;
    }

    public int getCount() {
        return itemList.size();
    }

    public Item getItem(int position) {
        return itemList.get(position);
    }

    public long getItemId(int position) {
        return itemList.get(position).hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder holder = null;
        Item item = (Item)getItem(position);
        View viewToUse = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            if(useList){
                viewToUse = mInflater.inflate(R.layout.simple_list_item_2, null);
            }
            else{
                viewToUse = mInflater.inflate(R.layout.example_grid_item, null);
            }
        }
        else {
            viewToUse = convertView;
            holder = (ItemHolder) viewToUse.getTag();
        }

        holder.itemNameView.setText(item.getName());
        return viewToUse;
    }

	/* *********************************
	 * We use the holder pattern
	 * It makes the view faster and avoid finding the component
	 * **********************************/

    private static class ItemHolder {
        public TextView itemNameView;
    }


}