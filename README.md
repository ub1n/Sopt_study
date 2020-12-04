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

