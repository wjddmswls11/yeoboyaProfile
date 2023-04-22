package com.example.yeoboyaprofile

import MyViewPagerAdapter
import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.yeoboyaprofile.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewpager: ViewPager2
    private lateinit var indicatorRecyclerView: RecyclerView
    private lateinit var indicatorAdapter: IndicatorAdapter


    private lateinit var recview_idol: RecyclerView
    private lateinit var aboutAdapter: AboutAdapter


    private lateinit var idolList: ArrayList<String>

    private var isDialogShowing = false


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뷰바인딩 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val imageList = listOf(
            R.drawable.img_painter_01,
            R.drawable.img_painter_02,
            R.drawable.img_painter_03,
            R.drawable.img_painter_04
        )

        val itemList = listOf(
            ItemData("Firstname", imageList.random(), imageList.random(), "Mark Serra", ""),
            ItemData("Sexual Orientation", imageList.random(), imageList.random(), "Female", ""),
            ItemData("Ethnicity", imageList.random(), imageList.random(), "MiddleEastern", ""),
            ItemData("Height", imageList.random(), imageList.random(), "180cm", ""),
            ItemData("Body type", imageList.random(), imageList.random(), "Dignified", ""),
            ItemData("Job", imageList.random(), imageList.random(), "Model", ""),
            ItemData("Personality", imageList.random(), imageList.random(), "Easygoing", ""),
            ItemData("Smoke", imageList.random(), imageList.random(), "Trying to quit", ""),
            ItemData("Drink", imageList.random(), imageList.random(), "Only socially", ""),
            ItemData("Religion", imageList.random(), imageList.random(), "Christianity", ""),
            ItemData(
                "Education",
                imageList.random(),
                imageList.random(),
                "High School Graduate",
                ""
            ),
            ItemData("Language", imageList.random(), imageList.random(), "Japanese", ""),
            ItemData("Child", imageList.random(), imageList.random(), "None", ""),
            ItemData("Parenting Plan", imageList.random(), imageList.random(), "Discuss", ""),
            ItemData(
                "Interests",
                imageList.random(),
                imageList.random(),
                "Movie,Party,Small Group",
                "Puzzle, Cook, Play, Drama"
            ),
            ItemData(
                "Favorite Food",
                imageList.random(),
                imageList.random(),
                "Pizza, Sandwich, Hambuger,",
                "Risotto, Wine"
            ),
            ItemData(
                "Exercise",
                imageList.random(),
                imageList.random(),
                "Soccer, Karate, Running,",
                "Tennis, Fencing"
            )
        )

        binding.recviewIdol.adapter = AboutAdapter(itemList)
        recview_idol = binding.recviewIdol


        recview_idol.layoutManager = LinearLayoutManager(this)
        recview_idol.setHasFixedSize(true)
        //뷰 요소 참조
        viewpager = binding.viewpager
        indicatorRecyclerView = binding.indicatorRecyclerView


        //어댑터 설정
        idolList = createIdolList()
        viewpager.adapter = MyViewPagerAdapter(idolList)
        viewpager.setCurrentItem(Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % idolList.size, false)
        indicatorAdapter = IndicatorAdapter(createIdolList().size)
        indicatorRecyclerView.adapter = indicatorAdapter

        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //인디케이터 리사이클러뷰 레이아웃 관리자 설정
        indicatorRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        //뷰페이저 페이지 변경 리스너 추가
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //인디케이터 어댑터의 현재 위치 업데이트
                val realPosition = position % idolList.size
                indicatorAdapter.setCurrentPosition(realPosition)
            }
        })

        //스크롤뷰 참조

        val scrollView = binding.ScrollView
        val cvSecond = binding.cvSecond
        val positionVisibility = binding.positionVisivility
        val constraint_scrolltop = binding.constraintScrolltop
        val animationDuration = 300L //애니메이션 시간 설정
        val imgflButton = binding.imgflButton
        val cvcrlFirst = binding.CvcrlFirst


        scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (cvSecond.y <= scrollY) {
                positionVisibility.visibility = View.VISIBLE
                positionVisibility.animate().translationY(0f).alpha(1f).setDuration(animationDuration).start()
                constraint_scrolltop.visibility = View.GONE
            } else {
                positionVisibility.animate().translationY(-positionVisibility.height.toFloat()).alpha(0f).setDuration(animationDuration).withEndAction {
                    positionVisibility.visibility = View.GONE
                }.start()
                constraint_scrolltop.visibility = View.VISIBLE
            }

            if (cvcrlFirst.y <= scrollY) {
                imgflButton.visibility = View.VISIBLE
                imgflButton.animate().alpha(1f).setDuration(animationDuration).start()
            } else {
                imgflButton.animate().alpha(0f).setDuration(animationDuration).withEndAction {
                    imgflButton.visibility = View.GONE
                }.start()
            }

        }


        // 누르면 상단으로

        imgflButton.setOnClickListener {
            scrollView.smoothScrollTo(0,0)
        }





        //다이어로그
        val view2 = binding.view2
        val createPopupClickListener = { anchorView: View, calculateX: (Int) -> Int, calculateY: (Int) -> Int ->
            View.OnClickListener {
                // item_dialog.xml 레이아웃을 가져옵니다.
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val dialogView = inflater.inflate(R.layout.item_dialog, null)

                // PopupWindow를 생성하고 설정합니다.
                val popupWindow = PopupWindow(dialogView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                popupWindow.isOutsideTouchable = true
                popupWindow.isFocusable = true
                popupWindow.elevation = 10f

                // 상단 오른쪽에 나타나도록 앵커 뷰(anchorView)의 위치를 계산합니다.
                val location = IntArray(2)
                anchorView.getLocationOnScreen(location)

                // x좌표와 y좌표를 계산합니다.
                val x = calculateX(location[0])
                val y = calculateY(location[1])

                // PopupWindow를 표시합니다.
                popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, x, y)
            }
        }

        // view2에 클릭 리스너를 설정합니다.
        view2.setOnClickListener(createPopupClickListener(view2, { locX -> locX - view2.width }, { locY -> locY + view2.height }))

        // img_btn_detail_more에 클릭 리스너를 설정합니다.
        val imgBtnDetailMore = binding.imgBtnDetailMore
        imgBtnDetailMore.setOnClickListener(createPopupClickListener(imgBtnDetailMore, { locX -> locX - imgBtnDetailMore.width }, { locY -> locY + imgBtnDetailMore.height}))










        // 이미지뷰 참조
        val imageView = findViewById<ImageView>(R.id.img_custom_profile)

        // 이미지 URL 리스트 생성
        val idolList = createIdolList()

        // 첫 번째 이미지 URL 로드 및 설정
        Glide.with(this)
            .load(idolList[0])
            .transform(CircleCrop())
            .into(imageView)





        Glide.with(this).apply {
            load("https://img1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/1V4H/image/WViUdVA3-dyWp8R4NXwCKy-sDiY.jpg")
                .into(binding.imgPhotoVideo1)

            load("https://m.upinews.kr/data/upi/image/20190415/p1065586576823565_363_thum.JPG")
                .into(binding.imgPhotoVideo2)

            load("https://spnimage.edaily.co.kr/images/Photo/files/NP/S/2019/04/PS19041500236.jpg")
                .into(binding.imgPhotoVideo3)
            load("https://img.mbn.co.kr/filewww/news/other/2013/04/04/402405422050.jpg")
                .into(binding.imgPhotoVideo4)
            load("https://t1.daumcdn.net/cfile/tistory/2105B84A582D81331B")
                .into(binding.imgPhotoVideo5)
        }

    }


    // 뷰 페이저에 들어갈 아이템
    private fun createIdolList(): ArrayList<String> {
        return arrayListOf(
            "https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/201604/21/htm_20160421164314998976.jpg",
            "https://blog.kakaocdn.net/dn/ckEIHR/btqDxJfIij9/vttG1SufQGmcgFazoTHDp1/img.jpg",
            "https://cdn.huffingtonpost.kr/news/photo/201904/82131_156077.jpeg"
        )
    }
}