package kr.team12.hackathon.dear.dao;

import kr.team12.hackathon.dear.dto.DataBean;
import kr.team12.hackathon.dear.dto.JsonBean;
import kr.team12.hackathon.dear.dto.TextBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by HunJin on 2016-05-29.
 */
public interface TextService {
    // 겟 방식으로 url 파싱
    @GET("list")
    Call<TextBean> get();

    @POST("list")
    Call<JsonBean> getData(@Body DataBean dataBean);
}
