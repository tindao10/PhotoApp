package com.example.photoapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//public class ArticleData {
//    public static ArrayList<Article> generatePhotoData(){
//        ArrayList<Article> photos =new ArrayList<>();
//        photos.add(new Article(0,"https://caphethuonghang.vn/wp-content/uploads/2020/06/ArabicHoney-600x600.jpg","milk","Arabica Cầu Đất thượng hạng có vị chua thanh xen lẫn với vị đắng nhẹ, nước pha màu nước nâu nhạt, trong trẻo của hổ phách. Mùi hương của Arabica rất thanh tao, quý phái, Arabica có mùi của hoa trái, hòa quyện với mùi của mật ong, và cà mùi bánh mì nướng, mùi của cánh đồng rơm buổi trưa hè…"));
//        photos.add(new Article(1,"https://bizweb.dktcdn.net/100/139/060/products/hanh-nhan-hat-chia-khong-duong-1l.png","Sữa Hạnh Nhân","Với sản phẩm tươi sống, trọng lượng thực tế có thể chênh lệch khoảng 10%.\n" +
//                "\n" +
//                "Giá sản phẩm trên TriFarm đã bao gồm thuế theo luật hiện hành."));
//        photos.add(new Article(2,"https://cdn.shopify.com/s/files/1/0617/2497/files/nam-linh-chi-trang_f714aa6a-9d11-49fd-ac37-0017607f42c7_480x480.png?v=1635501240","nấm","Nấm thủy tiên trắng Nhật Bản là một trong những loại nấm thuộc họ nấm thủy tinh, với màu trắng tinh khiết, gợi đến sự thuần khiết từ tự nhiên, nấm thủy tiên trắng luôn là loại nấm được thực khách lựa chọn. Nấm thủy tiên trắng có hương thơm của Nấm linh chi."));
//        photos.add(new Article(3,"https://salt.tikicdn.com/cache/w1200/ts/product/02/50/38/4ecfb025ef4a74aa3041e8fd71352bbc.png","mứt vải","Vina Mứt Vải là Dòng sản phẩm chuyên dụng pha chế các thức uống. Hương trái cây tự nhiện, đậm vị dễ dàng pha trộn với các dòng sản phẩm khác. Hương thơm nhẹ của vải cùng vị ngon ngọt. Mứt thích hợp dùng để pha chế."));
//        return photos;
//    }
//    public static Article getPhotoFromId(int id){
//        ArrayList<Article> phs =generatePhotoData();
//        for (int i=0;i < phs.size();id++)
//            if (phs.get(i).getId()==id)
//               return phs.get(i);
//         return null;
//    }
//}



public class ArticleData extends AsyncTask<String, String, String> {




    private Context context;
    private GridView gridView;
    public static ArticleList data;


    public ArticleData(Context context, GridView gridView) {
        this.context = context;
        this.gridView = gridView;
    }


    public static Article getPhotoFromId(int id) {
        for (int i = 0; i < data.getArticles().size(); i++)
            if (data.getArticles().get(i).getArticle_id() == id)
                return data.getArticles().get(i);
        return null;
    }


    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;


        try {
            URL url = new URL("https://raw.githubusercontent.com/thanhdnh/json/main/products.json");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.disconnect();
                if (reader != null)
                    reader.close();
            } catch  (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onPostExecute (String result) {
        super.onPostExecute(result);
        Gson gson = new Gson();
        data = gson.fromJson(result, (Type) ArticleList.class);
        ArticleAdapter adapter = new ArticleAdapter(data.getArticles(), context);
        gridView.setAdapter(adapter);
    }
}



