# Sopt_Study
## 1주차 과제
### <필수과제 - SignUpActivity 만들기>
SignActivity로 구현, 빈칸이 있을 경우 Toast메시지 출력 
비밀번호 * 표기 완료
### <성장 과제- StartActivityForResult>
startActivityForResult 이용해 SignActivity에서 MainActivity로 돌아올 경우 회원 가입한 id,pw값 그대로 출력 
### <성장 과제 2 - SharedPreferences()>
 MySharedPreference.kt에 SharedPreferences()값을 저장  
 App.kt에 SharedPreferences()초기화  
 이 후 로그인 창에서 id기억,pw기억, 자동로그인 세개의 체크박스를 이용해 값 기억, 자동 로그인의 기능 구현  
 로그인시 일단 세미나때 했던 Layout으로 가도록 임시로 구현
 
 ## 2wnck rhkwp
 ### <필수과제 - 화면 완성>
 클릭 시 상세화면 구현, 상세화면에서 정보 보여주기
 ####SampleAdapter - onBindViewHolder
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
 ####SampleActivity
 ```
 sample_rcv.layoutManager = GridLayoutManager(this,2)
 ```
 뒤에 2를 넣어서 2칸씩 나오도록 구현
 
 ### <성장 과제 - onSwipe와 onMove 구현>
 onSwipe를 통해 삭제 시 액션을 구현, onMove를 통해 이동 시 액션을 구현
 ####SampleActivity
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
####SampleAdapter
            
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
