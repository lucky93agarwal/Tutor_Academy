package com.mypersonal.tutor.retrofit;

import com.mypersonal.tutor.Models.CitySignupRequestJson;
import com.mypersonal.tutor.Models.CitySignupResponseJson;
import com.mypersonal.tutor.Models.LoginRequestJson;
import com.mypersonal.tutor.Models.LoginResponseJson;
import com.mypersonal.tutor.Models.RatingRequestJson;
import com.mypersonal.tutor.Models.RequestListRequestJson;
import com.mypersonal.tutor.Models.RequestResponseJson;
import com.mypersonal.tutor.Models.RequestResponseRequestJson;
import com.mypersonal.tutor.Models.RequsetResponseRequestJson;
import com.mypersonal.tutor.Models.StudentSignupRequestJson;
import com.mypersonal.tutor.Models.StudentSignupResponseJson;
import com.mypersonal.tutor.Models.SubjectResponseJson;
import com.mypersonal.tutor.Models.TutorListRequestJson;
import com.mypersonal.tutor.Models.TutorListResponseJson;
import com.mypersonal.tutor.Models.TutorRazorpayRequestJson;
import com.mypersonal.tutor.Models.TutorRequestListRequestJson;
import com.mypersonal.tutor.Models.TutorResponseListRequestJson;
import com.mypersonal.tutor.Models.TutorSignupRequestJson;
import com.mypersonal.tutor.Models.TutorSignupResponseJson;
import com.mypersonal.tutor.Models.TutorStudentRequestListRequestJson;
import com.mypersonal.tutor.Models.TutorStudentResponseListRequestJson;
import com.mypersonal.tutor.Models.TutorYourResponseListRequestJson;
import com.mypersonal.tutor.Models.WalletResponseList;
import com.mypersonal.tutor.Models.messageRequestJson;
import com.mypersonal.tutor.Models.timerResponseJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("student_signup.php")
    Call<StudentSignupResponseJson> student_signup(@Body StudentSignupRequestJson param);



    @POST("tutor_app_singup.php")
    Call<LoginResponseJson> login_data(@Body LoginRequestJson param);




    @POST("city.php")
    Call<CitySignupResponseJson> city_list(@Body CitySignupRequestJson param);


    @POST("timelist.php")
    Call<timerResponseJson> time_list(@Body CitySignupRequestJson param);


    @POST("subject.php")
    Call<SubjectResponseJson> subject_list(@Body CitySignupRequestJson param);


    @POST("tutor_signup.php")
    Call<TutorSignupResponseJson> tutor_signup(@Body TutorSignupRequestJson param);




    @POST("tutor_payment.php")
    Call<TutorSignupResponseJson> tutor_razorpay(@Body TutorRazorpayRequestJson param);






    @POST("subject_tutorlist.php")
    Call<TutorListResponseJson> tutor_listsss(@Body TutorListRequestJson param);




    @POST("requestlistapi.php")
    Call<TutorResponseListRequestJson> tutor_requset_listsss(@Body TutorRequestListRequestJson param);




    @POST("tutor_request_list_api.php")
    Call<TutorResponseListRequestJson> tutor_requset_listsssddd(@Body TutorRequestListRequestJson param);




    @POST("studentlist.php")
    Call<TutorResponseListRequestJson> tutor_student_listsss(@Body TutorRequestListRequestJson param);





    @POST("wallet.php")
    Call<WalletResponseList> wallet_amount(@Body TutorRequestListRequestJson param);






    @POST("messagedetail.php")
    Call<TutorResponseListRequestJson> tutor_msg_listsss(@Body TutorRequestListRequestJson param);





    @POST("message_tutor_to_student.php")
    Call<TutorYourResponseListRequestJson> tutor_msg_listsssxxx(@Body TutorRequestListRequestJson param);



    @POST("requestlistforstudent.php")
    Call<TutorStudentResponseListRequestJson> tutor_student_requset_listsss(@Body TutorStudentRequestListRequestJson param);




    @POST("requestlistforstudent_tutor.php")
    Call<TutorStudentResponseListRequestJson> tutor_student_requset_listsssjjj(@Body TutorStudentRequestListRequestJson param);






    @POST("messagelistforstudent.php")
    Call<TutorStudentResponseListRequestJson> tutor_student_message_listsss(@Body TutorStudentRequestListRequestJson param);



    @POST("messagetutor_to_student.php")
    Call<TutorStudentResponseListRequestJson> tutor_student_message_listssseee(@Body TutorStudentRequestListRequestJson param);



    @POST("tutorlist.php")
    Call<TutorListResponseJson> tutor_list(@Body TutorListRequestJson param);


    @POST("requestapi.php")
    Call<RequestResponseJson> request_list(@Body RequestListRequestJson param);


    @POST("tutor_requestapi.php")
    Call<RequestResponseJson> request_tutor_list(@Body RequestListRequestJson param);




    @POST("request_response.php")
    Call<RequsetResponseRequestJson> request_li(@Body RequestResponseRequestJson param);



    @POST("tutor_request_response.php")
    Call<RequsetResponseRequestJson> request_li_new(@Body RequestResponseRequestJson param);




    @POST("cancel_request_response.php")
    Call<RequsetResponseRequestJson> cancel_request_li(@Body RequestResponseRequestJson param);



    @POST("tutor_cancel_request_response.php")
    Call<RequsetResponseRequestJson> cancel_request_li_new(@Body RequestResponseRequestJson param);





    @POST("check_rating.php")
    Call<RequestResponseJson> check_get(@Body RequestListRequestJson param);







    @POST("getRating.php")
    Call<RequestResponseJson> rating(@Body RatingRequestJson param);





    @POST("messageapi.php")
    Call<RequestResponseJson> get_message(@Body messageRequestJson param);



    @POST("tutor_messageapi.php")
    Call<RequestResponseJson> get_student_message(@Body messageRequestJson param);




    @POST("res_msg.php")
    Call<RequestResponseJson> sebd_message(@Body messageRequestJson param);




    @POST("res_msg_tutor.php")
    Call<RequestResponseJson> sebd_message_send(@Body messageRequestJson param);

}
