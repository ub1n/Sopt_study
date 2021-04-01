# Sopt_Study
## 1주차 과제
### <필수과제 - SignUpActivity 만들기>
SignActivity로 구현, 빈칸이 있을 경우 Toast메시지 출력 
비밀번호 * 표기 완료
### <성장 과제- StartActivityForResult>
startActivityForResult 이용해 SignActivity에서 MainActivity로 돌아올 경우 회원 가입한 id,pw값 그대로 출력 

-MainActivity
```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode ==RESULT_OK) {
            when (requestCode) {
                100 -> {

                    id_edit.setText(data!!.getStringExtra("id").toString())
                    pw_edit.setText( data!!.getStringExtra("pw").toString())
                }
            }
        }
    }
```
미리 지정한 100의 값이 오면 SignActivity에서 온것으로 간주하고 위의 코드를 실행
### <성장 과제 2 - SharedPreferences()>
 MySharedPreference.kt에 SharedPreferences()값을 저장  
 
 -MySharedPreferences.kt
 ```
 class MySharedPreferences(context: Context) {

    val PREFS_FILENAME="prefs"
    val PREF_KEY_MY_EDITTEXT="loginId"
    val prefs:SharedPreferences=context.getSharedPreferences(PREFS_FILENAME,0)

    //sahredPreferences에 저장하려는 변수, get set은 추가 지정
    var loginId: String
        get()= prefs.getString(PREF_KEY_MY_EDITTEXT,"")!!
        set(value)=prefs.edit().putString(PREF_KEY_MY_EDITTEXT,value).apply()
    var myCheckbox: Boolean
        get()=prefs.getBoolean("myCheckbox",false)
        set(value)=prefs.edit().putBoolean("myCheckbox",value).apply()
    var loginPw: String
        get()=prefs.getString("loginPw","")!!
        set(value)=prefs.edit().putString("loginPw",value).apply()
    var myCheckId: Boolean
        get()=prefs.getBoolean("myCheckId",false)
        set(value)=prefs.edit().putBoolean("myCheckId",value).apply()
    var myCheckPw: Boolean
        get()=prefs.getBoolean("myCheckPw",false)
        set(value)=prefs.edit().putBoolean("myCheckPw",value).apply()

}
```
 App.kt에 SharedPreferences()초기화  
 
 -App.kt
 ```
 class App: Application() {
    companion object{
        lateinit var prefs:MySharedPreferences
    }

    override fun onCreate() {
        prefs=MySharedPreferences(applicationContext)
        super.onCreate()
    }
}
```
 이 후 로그인 창에서 id기억,pw기억, 자동로그인 세개의 체크박스를 이용해 값 기억, 자동 로그인의 기능 구현  
 ```
 login_check.setOnClickListener{
            App.prefs.myCheckbox=login_check.isChecked //체크박스 상태저장
        }
        id_check.setOnClickListener{
            App.prefs.myCheckId=id_check.isChecked   //이메일 체크박스 상태 저장
        }
        pw_check.setOnClickListener{
            App.prefs.myCheckPw=pw_check.isChecked  //비밀번호 체크박스 상태저장
        }
 ```
 로그인시 일단 세미나때 했던 Layout으로 가도록 임시로 구현 -> 2주차: SampleActivity로 
 
 ## 2주차 과제
 ### <필수과제 - 화면 완성>
 클릭 시 상세화면 구현, 상세화면에서 정보 보여주기
 
 -SampleAdapter - onBindViewHolder
 ```
 holder.itemView.setOnClickListener {view->
            var intent= Intent(view.context, SampleTouchActivity::class.java)
            intent.putExtra("title",data[position].title)
            intent.putExtra("subTitle",data[position].subTitle)
            intent.putExtra("image",data[position].image)
            intent.putExtra("day",data[position].day)
            intent.putExtra("text",data[position].text)

            view.context.startActivity(intent)
        }
 ```
 다음과같이 onClick을 구현하고 SampleTouchActivity에서 값을 받는다
 
 -SampleTouchActivity
 ```
 var title = getIntent().getStringExtra("title")
        var subTitle=getIntent().getStringExtra("subTitle")
        var image=getIntent().getIntExtra("image",0)
        var day=getIntent().getStringExtra("day")
        var text=getIntent().getStringExtra("text")

        touch_day.text=day
        touch_title.text=title
        touch_subtitle.text=subTitle
        touch_text.text=text
        touch_image.setImageResource(image)
```
### <성장 과제 - RecyclerView GridLayout 구성>
 기본 화면을 LienearLayoutManager에서 GridLayoutManager를 사용하여 구현
 
 -SampleActivity
 ```
 sample_rcv.layoutManager = GridLayoutManager(this,2)
 ```
 뒤에 2를 넣어서 2칸씩 나오도록 구현
 
 ### <성장 과제 - onSwipe와 onMove 구현>
 onSwipe를 통해 삭제 시 액션을 구현, onMove를 통해 이동 시 액션을 구현
 
 -SampleActivity
```
val simpleItemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
```
SampleActivity 안의 onCreate에 callback 함수를 구현
```
override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val adapter = sample_rcv.adapter as SampleAdapter
                adapter.onItemMoved(viewHolder!!.adapterPosition, target!!.adapterPosition)

                return true
            }

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.DOWN or ItemTouchHelper.UP
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
            }
```
getMovementFlags를 통해 움직임을 호출해주고, onMove를 통해 adapter에 구현한 onItemMoved를 호출해준다
```
override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                
                val position = viewHolder.adapterPosition
                

                val adapter = sample_rcv.adapter as SampleAdapter
                adapter.remove(position)


            }
```
삭제되는 아이템의 포지션을 가져오고 어댑터에 구현한 remove를 호출한다

-SampleAdapter
            
```
fun remove(position:Int){
 data.removeAt(position)
 notifyItemRemoved(position)
 }
fun onItemMoved(from:Int,to:Int){
     if(from==to){
         return
     }
     val fromItem=data.removeAt(from)
     data.add(to,fromItem)
     notifyItemMoved(from,to)
 }
 ```
어댑터에 다음과같이 추가적으로 함수를 구현해준다.

 ## 3주차 과제
 ### <필수과제 - 화면 완성 : 첫번 째 화면>
 프로필이 담긴 ConstraintLayout 아래 ViewPager와 TabLayout을 배치하여 첫 화면에 대한 Fragment도 두개 만들어주었다.
 
 -SampleFragment.kt -> onViewCreated()
 ```
 override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_sample, container, false)
        val viewPager=view.findViewById<ViewPager>(R.id.first_viewPager)
        val taplayout=view.findViewById<TabLayout>(R.id.first_tab)
        viewpagerAdapter=ViewPagerAdapter(childFragmentManager)
        viewpagerAdapter.fragments=listOf(InfoFragment(),OtherFragment())
        viewPager.adapter=viewpagerAdapter

        taplayout.setupWithViewPager(viewPager)
        taplayout.apply{
            getTabAt(0)?.text="INFO"
            getTabAt(1)?.text="OTHER"
        }
   ```
   
   일반적인 TabLayout처럼 구성하였지만 Activity와는 다르게
   supportFragmentManager를 사용하지 않고 childFragmentManager를 사용하였다.
   
   ### <필수 과제 - 화면구성: 두번 째 화면>
   두번 째 화면은 지난 2주차에 구성한 화면을 Fragment로 구성
   기존 코드는 복붙하여 넣었지만 Activity->Fragment로 되었기에 일부 수정하였다.
   
   -SampleFragment2.kt
   ```
   val samplercv=view.findViewById<RecyclerView>(R.id.sample_rcv2)   //fragment에서는 findviewById로 가져와야함
        sampleAdapter = SampleAdapter(view.context)
   ```
  
   마찬가지로 onViewCreated 안이지만 fragment에서는 sample_rcv2를 그대로 사용할수가 없기에(그대로 사용하면 비어있다고 오류남)
   앞서 정의한 view를 이용해 findViewById를 해준 후 사용하였다.
   또한 this 역시 사용 불가능하기때문에 SampleAdapter에서는 view.context로 context를 반환받았다.
   이외의 코드는 동일하다.
   
   
 ## 6주차 과제
 ### <필수과제 - 회원가입,로그인 서버연동>
 Retrofit Generator는 배운것과 동일하게 적용하였고 이후 RetrofitService 인터페이스를 만들었다.
 
 ```
 interface RetrofitService {
    @Headers("Content-Type: application/json")
    @POST("/users/signup")
    fun postSignup(@Body body : RequestSignupBody) : Call<ResponseSignupBody>

    @Headers("Content-Type: application/json")
    @POST("/users/signin")
    fun postSignin(@Body body : RequestSigninBody) : Call<ResponseSigninBody>


}
```
당연히 안에 body인 Request와 Call의 body인 Response는 각각 구현하였다.
  
이 후 회원가입이 있던 SignActivity에 다음과같이 서버 구현을 한 함수를 버튼을 누르면 실행이 되도록 추가해주었다.

-SignActivity
```
private fun PostServer(email: String, password: String,userName:String) {
        val postRequest=RequestSignupBody(email,password,userName)
        val call =
            RetrofitGenerator.create().postSignup(postRequest)
        call.enqueue(object : Callback<ResponseSignupBody> {
            override fun onResponse(call: Call<ResponseSignupBody>, response: Response<ResponseSignupBody>) {
                if (response.isSuccessful == false) {
                    if (response.code() == 400) {
                        //JSONObject를 활용해서 에러 객체를 받아올 수 있다
                        val ob=JSONObject(response.errorBody()?.string())

                        Toast.makeText(applicationContext,ob.getString("message"),Toast.LENGTH_SHORT).show()
                    } else {

                    }
                } else {
                    Toast.makeText(applicationContext,"회원가입에 성공했습니다",Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<ResponseSignupBody>, t: Throwable) {
                Toast.makeText(applicationContext,"회원가입에 실패했습니다 ",Toast.LENGTH_SHORT).show()
            }
        })
    }
```
response code가 400일때의 에러는 두가지가 있어서 다음과같이 400일 경우에 JSONObject를 활용하여 에러 바디에있는 에러 사유를 띄워주도록 하였다.
 
마찬가지로 로그인을 위해 Main에서도 다음과같이 함수를 구현하였으며 함수 내에서 서버연동 후 Intent를 옮기는 방식으로 하였다.

-Main Activity
```
private fun PostServer(email: String, password: String) {
        val postRequest= RequestSigninBody(email,password)
        val call =
            RetrofitGenerator.create().postSignin(postRequest)
        call.enqueue(object : Callback<ResponseSigninBody> {
            override fun onResponse(call: Call<ResponseSigninBody>, response: Response<ResponseSigninBody>) {
                if (response.isSuccessful == false) {
                    val ob= JSONObject(response.errorBody()?.string())
                } else {
                    Toast.makeText(applicationContext,"로그인에 성공했습니다",Toast.LENGTH_SHORT).show()
                    success=true
                    val intent = Intent(applicationContext, FragmentActivity::class.java)
                    applicationContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponseSigninBody>, t: Throwable) {
                Toast.makeText(applicationContext,"로그인에 실패했습니다 ",Toast.LENGTH_SHORT).show()
            }
        })
    }
```
###<성장과제 2- 카카오 웹 검색 API 추가>
KaKao Api는 별개의 서버주소를 또 사용하기 때문에 KaKaoRetrofitGenerator와 KaKaoRetrofitService를 추가로 구현하였다.
```
interface KaKaoRetrofitService {
    @Headers("Authorization: KakaoAK 06923f547440cd7014e8c066dc880462")
    @GET("/v2/search/web")
    fun getSearch(@Query ("query") query:String) : Call<ResponseSearchBody>

}
```
ResponseSearchBody의 모양은 특이하여 Documents라는 데이터 클래스를 만든 후 정의해주었다.
```
data class ResponseSearchBody(val meta:Meta,val documents:List<Documents>)
{
    data class Meta(val total_count:Int,val pageable_count:Int,val is_end:Boolean)
}
```
저기에 쓰이는 Meta는 한번에 구현해주었다.

이 후 빈 화면이었던 Fragment3에 검색 기능을 구현하였다.

-SampleFragment.kt

```
private fun PostGetServer(query:String){
        //Retrofit 서버 연결
        val call=KakaoRetrofitGenerator.create().getSearch(query)
        call.enqueue(object : Callback<ResponseSearchBody>{
            override fun onResponse(call: Call<ResponseSearchBody>?, response: Response<ResponseSearchBody>) {
                
                if(response?.isSuccessful==false){

                    Toast.makeText(activity!!.applicationContext,"${response?.code()}",Toast.LENGTH_LONG).show()
                }else {
                    try {
                        sAdapter.setGoalListItems(response?.body()?.documents!!)
                    } catch (e: Exception) {
                    }
                    if (response?.body() != null) {
                        sAdapter.setGoalListItems(response?.body()?.documents!!)
                    }



                }
            }
            override fun onFailure(call: Call<ResponseSearchBody>, t: Throwable) {

            }
        })
    }
 ```
 검색 이후에는 구현만 확인하기 위해서 Documents안에 있는 Title만 Recyclerview를 통해 띄워주는 것으로 하였다.
 검색을 누르면 리사이클러뷰에 검색 내용이 나오는데 , 이것은 위에 있는 setGoalListItems라는 함수를 Adapter에서 추가적으로 구현해주어서 가능하게하였다.
 
 -SearchAdapter.kt
 ```
 fun setGoalListItems(goalList: List<Documents>){
        this.goalList = goalList;
        notifyDataSetChanged()
    }
 ```
 비록 UI는 그지같지만 구현이 된것은 확인 할 수 있었다.. 
 
 ###PostMan 확인
 ![KakaoTalk_20201205_131612265](https://user-images.githubusercontent.com/54489627/101233611-67703f00-36fc-11eb-8f26-0f6af03e512a.png)
![KakaoTalk_20201205_131704373](https://user-images.githubusercontent.com/54489627/101233617-6ccd8980-36fc-11eb-847f-7ad5740e5278.png)

# 김우빈 인턴 - 담당부분 : 마이페이지 및 상세페이지

## 검색 필터 바텀시트 구성하고 뷰모델을 통해 값을 받아와 봅시다
### 바텀시트를 구성해주는 BottomSheetDialogFragment()
```
private fun filterClickListener(isFilterClicked: Boolean) {
        if (isFilterClicked) {
            val bottomSheetFragment = BottomWriteFragment()
            bottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                bottomSheetFragment.tag
            )
            mypageViewModel.scrapFilterOnClickFalse()
        }
    }
```
필터 클릭 리스너를 통해 만들어놓은 bottomSheetFragment 생성
```
private fun setWriteOnCheckedListener(checkId: Int) {
        when (checkId) {
            R.id.chip_write_1 -> category = binding.chipWrite1.text.toString()
            R.id.chip_write_2 -> category = binding.chipWrite2.text.toString()
            R.id.chip_write_3 -> category = binding.chipWrite3.text.toString()
            R.id.chip_write_4 -> category = binding.chipWrite4.text.toString()
            R.id.chip_write_5 -> category = binding.chipWrite5.text.toString()
            R.id.chip_write_6 -> category = binding.chipWrite6.text.toString()
            R.id.chip_write_7 -> category = binding.chipWrite7.text.toString()
            R.id.chip_write_8 -> category = binding.chipWrite8.text.toString()
        }
    }
```
바텀 시트의 칩 구성이 변경될경우 fragment내의 값을 변경
```
private fun applyFilter() {
        if (category != "" || range != "") {
            mypageViewModel.setWriteFilter(range, category)
        }
    }
```
이후 값이 변경된 값을 다음과 같이 라이브 데이터로 구성된 뷰모델의 필터 값에 넣음
```
private val _mywriteFilter=MutableLiveData<MyWriteFilter>()
    val mywriteFilter: LiveData<MyWriteFilter>
        get()=_mywriteFilter

    fun setWriteFilter(range:String,category:String){
        val myfilter=MyWriteFilter(range,category)
        _mywriteFilter.value=myfilter
    }
```
라이브데이터로 구성된 필터에서 값이변경되는 것을 관찰하기 위해 바텀 시트를 사용한 fragment에서
```
mypageViewModel.mywriteFilter.observe(viewLifecycleOwner) {
            getSheetDataListener(it)
        }
```
다음과 같이 observe를 구성하여 값이 바뀌면 바로 동작하도록 설정

## 맨 밑까지 스크롤시 더 보기 버튼을 띄워봅시다
### 스크롤시 이벤트를 처리해주는 OnScrollListener()
```
addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val lastVisiblePosition =
                        (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemTotalCount = adapter!!.itemCount - 1
                    if (lastVisiblePosition == itemTotalCount) {
                        binding.btnScrapShowmore.visibility = View.VISIBLE
                    } else {
                        binding.btnScrapShowmore.visibility = View.GONE
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
```
ScrollListener를 정의하여 값이 맨 밑에 올 경우 버튼을 visible로 바꾸고 스크롤이 다시 위로 가면 gone으로 바꿔서 버튼이 안보이게 구현


## 이미지 크롭으로 이미지를 원하는 비율로 잘라봅시다
### 이미지 크롭 이벤트를 처리해주는 Image Cropper
```
implementation 'com.theartofdev.edmodo:android-image-cropper:2.8.0'
```
우선 implemetion을 해주고 여기서 제공해주는 Image Crop Activity를 manifest에 정의
```
<activity
            android:name="com.theartofdev.edmodo.cropper.CropImageActivity"
            android:screenOrientation="portrait"
            android:theme="@style/Base.Theme.AppCompat" />
```
그 후 image를 고르는 화면으로 넘어가는 intent를 시작하는 함수를 만듬
```
private fun ImagePicker() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("crop", "true")
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
```
이 후 선택한 이미지를 크롭하기 위해 startActivityForResult를 정의
```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            CropImage.activity(data.data!!).setAspectRatio(4, 3).start(requireContext(), this)  // 4대 3 비율로 지정, 안에서 정해진 비율안에 이미지를 조절할 수 있다
            //Fragment에서 사용하였기 때문에 requireContext(), this를 사용, Activity에서는 this만 쓰면된다.
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) { //이미지 크롭 완료 버튼을 누르면 데이터 처리,
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                var resultUri = result.getUri() ///해당 resultUri를 활용하여 이미지를 사용하면 됩니다.
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.getError()
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
```

###크롭받은 이미지를 multipart로 만들어주자 (okhttp 4 버전)
```
                var resultUri = result.getUri()
                val options = BitmapFactory.Options()
                val inputStream = requireContext().contentResolver.openInputStream(resultUri)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
                val photoBody =
                    RequestBody.create( //이전 okhttp3 버전에서 쓰였던 Media.Parse가 사용이 불가능해졌기때문에 toMediaTypeOrNull을 사용해야한다.
                        "image/jpg".toMediaTypeOrNull(),
                        byteArrayOutputStream.toByteArray()
                    )
                val part = MultipartBody.Part.createFormData(
                    "image",
                    File(resultUri.toString()).name,
                    photoBody
                )
                mypageViewModel.putProfiles(part)
```
위에서 이어져서 다음과같이 멀티파트를 만들어서 보내면 이걸통해 서버와 통신가능(다른 추가적인 string 같은 body가 필요하다면 추가적인 multipart-form data로 보내야함

##Coordinator layout을 통해 위의 이미지를 사라지고 툴바를 상단에 고정시켜보자
```
<androidx.coordinatorlayout.widget.CoordinatorLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".mypage.view.MyPageFragment">
```
우선 layout을 지정해준다
```
<com.google.android.material.appbar.AppBarLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
```
이 후 하단 layout으로 AppBarLayout을 지정해준다 ->Coordinatorlayout으로 인해 변화하는 영역을 지정할 수 있다.
```
<com.google.android.material.appbar.CollapsingToolbarLayout
                android:id="@+id/toolbar_layout"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:fitsSystemWindows="true"
                app:contentScrim="@color/mypage_white"
                app:layout_scrollFlags="scroll|exitUntilCollapsed">
```
이 후 하단으로 스크롤을 하게되면 사라지는 영역을 지정하는 CollapsingToolbarLayout을 지정해준다.
즉 AppBarLayout에 포함이 되면서 CollapsingToolbarLayout에 포함이 된다면 하단으로 스크롤 할떄 사라지지않고 상단에 고정이 된다.
이 후에는 각각영역에 속하는 레이아웃을 지정하면 된다.

##정말 간단하게 이메일 양식받아 보내기
```
        val intent = Intent(Intent.ACTION_SEND)    //이메일을 보내는 액션을 지정
        intent.type = "plain/text"   
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("teambeme@naver.com"))  //받는 사람을 지정
        intent.putExtra(Intent.EXTRA_SUBJECT, "BeMe 유저 신고 ")  //메일의 제목 지정
        intent.putExtra( // 메일의 내용 지정
            Intent.EXTRA_TEXT, "1. 문의 유형 ( 문의, 버그 제보, 탈퇴하기, 기타) : \n" +
                    "2. 회원 닉네임 (필요시 기입) :\n" +
                    "3. 문의 내용 :\n" +
                    "\n" +
                    "문의하신 사항은 BeMe팀이 신속하게 처리하겠습니다. 감사합니다 :)"
        )
        intent.type = "message/rfc822"
        startActivity(intent)
```
#PJT1 - 홈페이지 구성
##PJT1 - 프론트 엔드
###<공통 Header구성>
-Header
```
<Header>
	<input type="button" value="홈"
	onClick="location.href='index.html'"
	class="headerButton" />
```
각 구성마다 button으로 작성, submit이 없기에 input type으로 지정하여 버튼생성
onClick을 통해 각 페이지로 이동하게 구현

###<Main 페이지 Section구성>
```
<section id="indexSection ">
	<h2 class="titleText">웹 프론트엔드를 잘하고 싶은 개발자!</h2>
	<p class="normalText">함께 개발하고 같이 성장하고싶어요<br>
	서울시 강북구 쪽에 살고 있어요!<br></p>
	<input type="button" value="자기소개"
		onClick="location.href='aboutme.html'"
		class="indexButton" />
	<input type="button" value="내사진"
		onClick="location.href='photo.html'"
		class="indexButton" /><br><br>
	<img src="images/mymap.png" width=70%/>
</section>
```
title은 titleText로 클래스를 지정하여 추후 aboutme.html에서 title로 쓸 텍스트와 엮어 한번에 사용
상세 설명 부분은 p 태그를 통해 문단 지정하고 normalText 클래스 지정하여 aboutme.html에서 내용으로 쓸 텍스트와 엮어 한번에 사용
버튼의 형식은 헤더의 공통 부분과 같게 설정
이미지는 width를 70%로 지정하여 화면이 변해도 유동적으로 조절되게 구현

#PTJ1 - 백엔드 구현

    -현재 시간을 LocalDateTime 클래스를 이용하여 구현하고 , 화면을 표시하기 위해 Fromatter를 이용하여 구현하였습니다.
    -index.html,aboutme.html,photo.html 모두 tomcat서버를 통해 동작되도록 했습니다.
    -naver-eclipse-formatter를 통해 코딩 컨벤션이 지켜지도록 했습니다.
    -(미구현) 시간 텍스트 위치를 화면 정중앙에 오도록 하는 것을 아직 하지 못하였습니다.

#PTJ1 - 프론트 구현

   ###index.html
        - 상단의 네비게이션 버튼들의 간격을 같게 했습니다.
        - 모든 콘텐츠가 가운데 정렬되도록 했습니다.
        - 버튼들에 모두 그림자 효과를 주고, 마우스 커서가 다가가면 손 모양으로 변경되게 하였습니다.

   ###aboutme.html 
        - 각 문단들의 간격 배치와 영역 간의 간격을 동일하게 유지하였습니다.
        - 같은 수준의 태그에서 같은 글자 크기를 유지했습니다.
    
   ###photo.html
        - 사진에 테두리를 만들었습니다.
        - 동일한 이미지와 글자들은 같은 크기를 유지하게 했습니다.
#수정사항
    ###백엔드 구현
        - 클래스 주석을 추가하였습니다.
        - 경로를 상대경로로 변경하였습니다.
        - close()를 없앴습니다.
        - -> 스트림은 컨테이너에 의해 열리므로 닫을 책임 역시 컨테이너에 있어서 나중에 컨테이너가 직접 닫아야 할 때 예외처리가 필요할수도 있기 때문이 맞나요??

   ###프론트 수정사항
        - 경로를 상대경로로 모두 통일하였습니다.
        - Button들을 nav를 이용한 태그로 변경하였습니다.
        - 클래스 이름을 컨벤션에 맞게 수정하였습니다.
        - 태그에 클래스 부여한 것들을 수정하였습니다.
        - style 관련 작업들은 css로 모두 이관하였습니다.
