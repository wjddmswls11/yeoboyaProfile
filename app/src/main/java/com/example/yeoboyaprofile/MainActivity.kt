package com.example.yeoboyaprofile

import MyViewPagerAdapter
import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.yeoboyaprofile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
            ItemData("Firstname", imageList.random(), imageList.random(), "Mark Serra" ),
            ItemData("Sexual Orientation", imageList.random(), imageList.random(), "Female"),
            ItemData("Ethnicity", imageList.random(), imageList.random(), "MiddleEastern", ),
            ItemData("Height", imageList.random(), imageList.random(), "180cm" ),
            ItemData("Body type", imageList.random(), imageList.random(), "Dignified", ),
            ItemData("Job", imageList.random(), imageList.random(), "Model" ),
            ItemData("Personality", imageList.random(), imageList.random(), "Easygoing" ),
            ItemData("Smoke", imageList.random(), imageList.random(), "Trying to quit" ),
            ItemData("Drink", imageList.random(), imageList.random(), "Only socially" ),
            ItemData("Religion", imageList.random(), imageList.random(), "Christianity" ),
            ItemData("Education", imageList.random(), imageList.random(), "High School Graduate" ),
            ItemData("Language", imageList.random(), imageList.random(), "Japanese" ),
            ItemData("Child", imageList.random(), imageList.random(), "None" ),
            ItemData("Parenting Plan", imageList.random(), imageList.random(), "Discuss"),
            ItemData("Interests",imageList.random(),imageList.random(),"Movie,Party,Small Group, Puzzle, Cook, Play, Drama"),
            ItemData("Favorite Food",imageList.random(),imageList.random(),"Pizza, Sandwich, Hambuger, Risotto, Wine"),
            ItemData("Exercise",imageList.random(),imageList.random(),"Soccer, Karate, Running, Tennis, Fencing")
        )



        with(binding) {
            recviewIdol.adapter = AboutAdapter(itemList)
            recviewIdol.layoutManager = LinearLayoutManager(this@MainActivity)

            //어댑터 설정
            viewPager.adapter = MyViewPagerAdapter(array)
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            // CircleIndicator3를 설정하고 ViewPager2와 연결합니다.
            circleIndicator.setViewPager(viewPager)
            circleIndicator.createIndicators(array.size, 0)
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val actualPosition = position % array.size
                    supportActionBar?.title = "Image ${(actualPosition + 1)} of ${array.size}"
                    circleIndicator.animatePageSelected(actualPosition)
                }
            })

            // 무한 스크롤을 위해 초기 페이지를 중간으로 설정
            val pageCount = 10000
            val initialPosition = (pageCount / 2) - ((pageCount / 2) % array.size)
            viewPager.setCurrentItem(initialPosition, false)


        }


        //Spannable
        val ddd = SpannableString(binding.tvAboutMe.text)
        val backColor = ContextCompat.getColor(this,R.color.primary_200)
        ddd.setSpan(
            CustomBackground(backColor),
            0,
            ddd.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvAboutMe.text = ddd

        //스크롤뷰 참조
        val animationDuration = 300L //애니메이션 시간 설정

        binding.apply {
            positionVisivility.apply {
                alpha = 0f
                layoutParams = (layoutParams as ConstraintLayout.LayoutParams).apply {
                    setMargins(0, -130 * resources.displayMetrics.density.toInt(), 0, 0)
                }
            }
        }

        with(binding) {
            val slideDownAnimation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.dialog_slide_down)
            ScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                val layoutParams = positionVisivility.layoutParams as ConstraintLayout.LayoutParams

                if (cvSecond.y <= scrollY) {
                    if (positionVisivility.alpha != 1f) {
                        layoutParams.setMargins(0, 0, 0, 0)
                        positionVisivility.layoutParams = layoutParams
                        positionVisivility.apply {
                            startAnimation(slideDownAnimation)
                            alpha = 1f
                        }
                    }
                } else {
                    if (positionVisivility.alpha != 0f) {
                        layoutParams.setMargins(0, -130 * resources.displayMetrics.density.toInt(), 0, 0)
                        positionVisivility.layoutParams = layoutParams
                        positionVisivility.apply {
                            clearAnimation()
                            alpha = 0f
                        }
                    }
                }

                if (CvcrlFirst.y <= scrollY) {
                    imgflButton.apply {
                        visibility = View.VISIBLE
                        animate().alpha(1f).setDuration(animationDuration).start()
                    }
                } else {
                    imgflButton.animate().alpha(0f).setDuration(animationDuration).withEndAction {
                        imgflButton.visibility = View.GONE
                    }.start()
                }

            }

            // 누르면 상단으로
            imgflButton.setOnClickListener {
                ScrollView.smoothScrollTo(0,0)
            }
        }


        // 팝업 생성 및 표시 함수
        fun createAndShowPopup(anchorView: View, calculateX: (Int) -> Int, calculateY: (Int) -> Int) {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogView = inflater.inflate(R.layout.item_dialog, null)

            val tvBlockDialog = dialogView.findViewById<TextView>(R.id.tv_block_dialog)
            val tv_report_dialog = dialogView.findViewById<TextView>(R.id.tv_report_dialog)

            val popupWindow = PopupWindow(dialogView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            popupWindow.apply {
                isOutsideTouchable = true
                isFocusable = true
                elevation = 10f
            }


            tvBlockDialog.setOnClickListener {
                popupWindow.dismiss()

                //AlertDialog 보여주기
                val alertDialogBuilder = AlertDialog.Builder(this)

                alertDialogBuilder.apply {
                    setMessage("Are you sure you want to block this user?")
                    setPositiveButton("Yes") { _, _ ->
                        // 'Yes' 버튼 클릭 시 처리
                        Toast.makeText(context, "User blocked", Toast.LENGTH_SHORT).show()
                    }
                    setNegativeButton("No") { dialog, _ ->
                        // 'No' 버튼 클릭 시 처리
                        dialog.dismiss()
                    }
                    create().show()
                }
            }
            tv_report_dialog.setOnClickListener {
                popupWindow.dismiss()

                //AlertDialog 보여주기
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.apply {
                    setMessage("Are you sure you want to report this content?")
                    setPositiveButton("Yes") { _, _ ->
                        // 'Yes' 버튼 클릭 시 처리
                        Toast.makeText(context, "Content reported", Toast.LENGTH_SHORT).show()
                    }
                    alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                        // 'No' 버튼 클릭 시 처리
                        dialog.dismiss()
                    }
                    alertDialogBuilder.create().show()
                }
            }

            val location = IntArray(2)
            anchorView.getLocationOnScreen(location)

            val x = calculateX(location[0])
            val y = calculateY(location[1])

            popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, x, y)
        }


        with(binding){
            // 다이어로그
            view2.setOnClickListener {
                createAndShowPopup(view2, { locX -> locX - view2.width * 2 }, { locY -> locY + view2.height })
            }

            imgBtnDetailMore.setOnClickListener {
                createAndShowPopup(imgBtnDetailMore, { locX -> locX - imgBtnDetailMore.width * 2}, { locY -> locY + imgBtnDetailMore.height - 35})
            }



            // 첫 번째 이미지 URL 로드 및 설정
            Glide.with(binding.root.context)
                .load(array[0])
                .transform(CircleCrop())
                .into(imgCustomProfile)




           val array2 = arrayListOf(
                "https://img1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/1V4H/image/WViUdVA3-dyWp8R4NXwCKy-sDiY.jpg",
                "https://m.upinews.kr/data/upi/image/20190415/p1065586576823565_363_thum.JPG",
                "https://spnimage.edaily.co.kr/images/Photo/files/NP/S/2019/04/PS19041500236.jpg",
                "https://img.mbn.co.kr/filewww/news/other/2013/04/04/402405422050.jpg",
                "https://t1.daumcdn.net/cfile/tistory/2105B84A582D81331B"
            )

            val photoVideoAdapter = PhotoVideoAdapter(array2)
            recviewPhotoVideo.apply {
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = photoVideoAdapter
            }

            val gridLayoutManager = GridLayoutManager(this@MainActivity, 3)
            recviewPhotoVideo.layoutManager = gridLayoutManager



            }
        }


    // 뷰 페이저에 들어갈 아이템
    val array = arrayListOf(
        "https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/201604/21/htm_20160421164314998976.jpg",
        "https://blog.kakaocdn.net/dn/ckEIHR/btqDxJfIij9/vttG1SufQGmcgFazoTHDp1/img.jpg",
        "https://cdn.huffingtonpost.kr/news/photo/201904/82131_156077.jpeg",
        "https://spnimage.edaily.co.kr/images/Photo/files/NP/S/2013/04/PS13040400099.jpg"
    )
}