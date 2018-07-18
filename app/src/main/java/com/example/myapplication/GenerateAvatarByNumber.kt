package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import java.util.*

/**
 * @data 07\12\2018 18\56
 * @author wcx
 * @description 根据输入数字随机生成图片
 */
class GenerateAvatarByNumber {

  /**
   * @date 07\12\2018
   * @author wcx
   * @description 根据输入数字随机生成图片,图片集合为空则使用默认图片
   */
  fun generateImage(randomNumber: Int,
                    context: Context,
                    resourceDrawable: ArrayList<Int>?): BitmapInfo {
    val imageArrayList: ArrayList<Int> = if (resourceDrawable == null || resourceDrawable.size == 0) {
      getDrawableArrayList()
    } else {
      resourceDrawable
    }

    val imageMaxSize = imageArrayList.size
    val absoluteRandomNumber = Math.abs(randomNumber)
    // 取模
    val modulusNumber = absoluteRandomNumber % imageMaxSize

    val randomColor = getRandomColor(absoluteRandomNumber * 30) // 获得背景颜色

    val iconDrawable = ContextCompat.getDrawable(context, imageArrayList[modulusNumber])

    val frontBitmap = convertDrawable2BitmapByCanvas(iconDrawable!!)

    val backgroundBitmap = Bitmap.createBitmap(iconDrawable.intrinsicWidth, iconDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    backgroundBitmap.eraseColor(Color.parseColor(randomColor))
    val combineBitmap = combineBitmap(backgroundBitmap, frontBitmap, absoluteRandomNumber)

    return BitmapInfo(modulusNumber, combineBitmap!!)
  }

  /**
   * @date 07/18/2018 15/55
   * @author wcx
   */
  private fun getDrawableArrayList(): ArrayList<Int> {
    val imageArrayList = ArrayList<Int>()
    val icBrownBear = R.drawable.ic_brown_bear
    val icCattle = R.drawable.ic_cattle
    val icChicken = R.drawable.ic_chicken
    val icEagle = R.drawable.ic_eagle
    val icElephant = R.drawable.ic_elephant
    val icElk = R.drawable.ic_elk
    val icFox = R.drawable.ic_fox
    val icFrogrita = R.drawable.ic_frogrita
    val icGiraffe = R.drawable.ic_giraffe
    val icHippo = R.drawable.ic_hippo
    val icJaguar = R.drawable.ic_jaguar
    val icKoala = R.drawable.ic_koala
    val icLion = R.drawable.ic_lion
    val icOrangutan = R.drawable.ic_orangutan
    val icOwl = R.drawable.ic_owl
    val icPenguin = R.drawable.ic_penguin
    val icRabbit = R.drawable.ic_rabbit
    val icRaccoon = R.drawable.ic_raccoon
    val icRhinoceros = R.drawable.ic_rhinoceros
    val icWolf = R.drawable.ic_wolf
    imageArrayList.add(icBrownBear)
    imageArrayList.add(icCattle)
    imageArrayList.add(icChicken)
    imageArrayList.add(icEagle)
    imageArrayList.add(icElephant)
    imageArrayList.add(icElk)
    imageArrayList.add(icFox)
    imageArrayList.add(icFrogrita)
    imageArrayList.add(icGiraffe)
    imageArrayList.add(icHippo)
    imageArrayList.add(icJaguar)
    imageArrayList.add(icKoala)
    imageArrayList.add(icLion)
    imageArrayList.add(icOrangutan)
    imageArrayList.add(icOwl)
    imageArrayList.add(icPenguin)
    imageArrayList.add(icRabbit)
    imageArrayList.add(icRaccoon)
    imageArrayList.add(icRhinoceros)
    imageArrayList.add(icWolf)
    return imageArrayList
  }

  /**
   * @date 07\16\2018 10\08
   * @author wcx
   */
  private fun getRandomColor(absoluteRandomNumber: Int): String {

    val colorAll = 171 * 6
    val colorMax = 220
    val colorMin = 84
    //取模
    val modulusNumber = absoluteRandomNumber % colorAll

    var redNextInt = colorMax
    var greedNextInt = colorMin
    var blueNextInt = colorMin

    val colorArea = modulusNumber / 171
    val colorChangeAmount = modulusNumber % 171

    when (colorArea) {
      0 -> greedNextInt += colorChangeAmount
      1 -> {
        redNextInt -= colorChangeAmount
        greedNextInt = colorMax
      }
      2 -> {
        redNextInt = colorMin
        greedNextInt = colorMax
        blueNextInt += colorChangeAmount
      }
      3 -> {
        redNextInt = colorMin
        greedNextInt = colorMax - colorChangeAmount
        blueNextInt = colorMax
      }
      4 -> {
        redNextInt = colorMin + colorChangeAmount
        greedNextInt = colorMin
        blueNextInt = colorMax
      }
      5 -> {
        redNextInt = colorMax
        greedNextInt = colorMin
        blueNextInt = colorMax - colorChangeAmount
      }
    }

    var redToHexString = Integer.toHexString(redNextInt)
    if (redToHexString.length < 2) {
      redToHexString = "0$redToHexString"
    }
    var greedToHexString = Integer.toHexString(greedNextInt)
    if (greedToHexString.length < 2) {
      greedToHexString = "0$greedToHexString"
    }
    var blueToHexString = Integer.toHexString(blueNextInt)
    if (blueToHexString.length < 2) {
      blueToHexString = "0$blueToHexString"
    }
    return "#$redToHexString$greedToHexString$blueToHexString"
  }

  /**
   * @date 07\12\2018 16\40
   * @author wcx
   * @description 合成图片
   */
  private fun combineBitmap(background: Bitmap?,
                            foreground: Bitmap,
                            absoluteRandomNumber: Int): Bitmap? {
    if (background == null) {
      return null
    }
    val backgroundWidth = background.width.toFloat()
    val backgroundHeight = background.height.toFloat()
    val foregroundWidth = foreground.width
    val foregroundHeight = foreground.height
    val newmap = Bitmap.createBitmap(backgroundWidth.toInt(), backgroundHeight.toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(newmap)
    canvas.drawBitmap(background, 0f, 0f, null)

    // 创建画笔
    val paint = Paint()
    paint.color = Color.WHITE
    val linearGradient = LinearGradient(backgroundWidth, 0f, backgroundWidth, backgroundHeight, Color.WHITE, Color.TRANSPARENT, Shader.TileMode.MIRROR)
    paint.shader = linearGradient
    paint.alpha = 100
    paint.isAntiAlias = true // 设置画笔的锯齿效果
    paint.isDither = true

    // 三角形坐标
    var firstTriangleFirstX = 0f
    var firstTriangleSecondX = 0f
    var firstTriangleEndX = 0f

    var firstTriangleFirstY = 0f
    var firstTriangleSecondY = 0f
    var firstTriangleEndY = 0f

    // 矩形坐标
    var rectangleFirstX = 0f
    var rectangleSecondX = 0f
    var rectangleThirdX = 0f
    var rectangleEndX = 0f

    var rectangleFirstY = 0f
    var rectangleSecondY = 0f
    var rectangleThirdY = 0f
    var rectangleEndY = 0f

    // 获得起始点所在区域
    val firstArea = (absoluteRandomNumber / 50) % 4

    val sin = Math.sin(Math.toRadians(20.toDouble()))
    val cos = Math.cos(Math.toRadians(20.toDouble()))

    val cosWidth = (backgroundWidth * cos).toFloat()
    val sinHeight = (backgroundWidth * sin).toFloat()

    val sinMobile = sinHeight / 2
    val speedMultiple = 11
    val coordinateMultiple = 8
    when (firstArea) {
      0 -> {
        // 三角坐标
        firstTriangleFirstX = backgroundWidth / 2 - cosWidth
        firstTriangleFirstY = backgroundHeight / 2 - sinHeight - sinMobile

        firstTriangleSecondX = backgroundWidth / 2 + cosWidth
        firstTriangleSecondY = backgroundHeight / 2 + sinHeight - sinMobile

        firstTriangleEndX = backgroundWidth / 2 - cosWidth
        firstTriangleEndY = backgroundHeight / 2 + sinHeight - sinMobile

        firstTriangleFirstY -= absoluteRandomNumber % 50 * speedMultiple / 2
        firstTriangleEndY += absoluteRandomNumber % 50 * speedMultiple

        // 四边形坐标
        rectangleFirstX = backgroundWidth / 2 - backgroundWidth / coordinateMultiple
        rectangleFirstY = backgroundHeight / 2 - cosWidth

        rectangleSecondX = backgroundWidth / 2 + backgroundWidth / coordinateMultiple
        rectangleSecondY = backgroundHeight / 2 - cosWidth

        rectangleThirdX = backgroundWidth / 2 + backgroundWidth / coordinateMultiple
        rectangleThirdY = backgroundHeight / 2 + cosWidth

        rectangleEndX = backgroundWidth / 2 - backgroundWidth / coordinateMultiple
        rectangleEndY = backgroundHeight / 2 + cosWidth

        rectangleThirdX += absoluteRandomNumber % 50 * speedMultiple * 2
        rectangleEndX -= absoluteRandomNumber % 50 * speedMultiple * 2
      }
      1 -> {
        // 三角坐标
        firstTriangleFirstX = backgroundWidth / 2 - sinHeight + sinMobile
        firstTriangleFirstY = backgroundHeight / 2 - cosWidth

        firstTriangleSecondX = backgroundWidth / 2 + sinHeight + sinMobile
        firstTriangleSecondY = backgroundHeight / 2 + cosWidth

        firstTriangleEndX = backgroundWidth / 2 - sinHeight + sinMobile
        firstTriangleEndY = backgroundHeight / 2 + cosWidth

        firstTriangleSecondX += absoluteRandomNumber % 50 * speedMultiple / 2
        firstTriangleEndX -= absoluteRandomNumber % 50 * speedMultiple

        // 四边形坐标
        rectangleFirstX = backgroundWidth / 2 - cosWidth
        rectangleFirstY = backgroundHeight / 2 - backgroundWidth / coordinateMultiple

        rectangleSecondX = backgroundWidth / 2 + cosWidth
        rectangleSecondY = backgroundHeight / 2 - backgroundWidth / coordinateMultiple

        rectangleThirdX = backgroundWidth / 2 + cosWidth
        rectangleThirdY = backgroundHeight / 2 + backgroundWidth / coordinateMultiple

        rectangleEndX = backgroundWidth / 2 - cosWidth
        rectangleEndY = backgroundHeight / 2 + backgroundWidth / coordinateMultiple

        rectangleSecondY -= absoluteRandomNumber % 50 * speedMultiple * 2
        rectangleThirdY += absoluteRandomNumber % 50 * speedMultiple * 2
      }
      2 -> {
        // 三角坐标
        firstTriangleFirstX = backgroundWidth / 2 + cosWidth
        firstTriangleFirstY = backgroundHeight / 2 - sinHeight - sinMobile

        firstTriangleSecondX = backgroundWidth / 2 + cosWidth
        firstTriangleSecondY = backgroundHeight / 2 + sinHeight - sinMobile

        firstTriangleEndX = backgroundWidth / 2 - cosWidth
        firstTriangleEndY = backgroundHeight / 2 + sinHeight - sinMobile

        firstTriangleFirstY -= absoluteRandomNumber % 50 * speedMultiple / 2
        firstTriangleSecondY += absoluteRandomNumber % 50 * speedMultiple

        // 四边形坐标
        rectangleFirstX = backgroundWidth / 2 - backgroundWidth / coordinateMultiple
        rectangleFirstY = backgroundHeight / 2 - cosWidth

        rectangleSecondX = backgroundWidth / 2 + backgroundWidth / coordinateMultiple
        rectangleSecondY = backgroundHeight / 2 - cosWidth

        rectangleThirdX = backgroundWidth / 2 + backgroundWidth / coordinateMultiple
        rectangleThirdY = backgroundHeight / 2 + cosWidth

        rectangleEndX = backgroundWidth / 2 - backgroundWidth / coordinateMultiple
        rectangleEndY = backgroundHeight / 2 + cosWidth

        rectangleFirstX -= absoluteRandomNumber % 50 * speedMultiple * 2
        rectangleSecondX += absoluteRandomNumber % 50 * speedMultiple * 2
      }
      3 -> {
        // 三角坐标
        firstTriangleFirstX = backgroundWidth / 2 + sinHeight - sinMobile
        firstTriangleFirstY = backgroundHeight / 2 + cosWidth

        firstTriangleSecondX = backgroundWidth / 2 - sinHeight - sinMobile
        firstTriangleSecondY = backgroundHeight / 2 - cosWidth

        firstTriangleEndX = backgroundWidth / 2 + sinHeight - sinMobile
        firstTriangleEndY = backgroundHeight / 2 - cosWidth

        firstTriangleSecondX -= absoluteRandomNumber % 50 * speedMultiple / 2
        firstTriangleEndX += absoluteRandomNumber % 50 * speedMultiple

        // 四边形坐标
        rectangleFirstX = backgroundWidth / 2 - cosWidth
        rectangleFirstY = backgroundHeight / 2 - backgroundWidth / coordinateMultiple

        rectangleSecondX = backgroundWidth / 2 + cosWidth
        rectangleSecondY = backgroundHeight / 2 - backgroundWidth / coordinateMultiple

        rectangleThirdX = backgroundWidth / 2 + cosWidth
        rectangleThirdY = backgroundHeight / 2 + backgroundWidth / coordinateMultiple

        rectangleEndX = backgroundWidth / 2 - cosWidth
        rectangleEndY = backgroundHeight / 2 + backgroundWidth / coordinateMultiple

        rectangleFirstY -= absoluteRandomNumber % 50 * speedMultiple * 2
        rectangleEndY += absoluteRandomNumber % 50 * speedMultiple * 2
      }
    }

    // 第一个三角形
    val firstTrianglePath = Path()
    firstTrianglePath.moveTo(firstTriangleFirstX, firstTriangleFirstY)
    firstTrianglePath.lineTo(firstTriangleSecondX, firstTriangleSecondY)
    firstTrianglePath.lineTo(firstTriangleEndX, firstTriangleEndY)
    firstTrianglePath.close()
    canvas.rotate((absoluteRandomNumber % 50).toFloat(), backgroundWidth / 2, backgroundHeight / 2)
    canvas.drawPath(firstTrianglePath, paint)

    // 第二个三角形
    canvas.rotate((absoluteRandomNumber % 50).toFloat() + 45, backgroundWidth / 2, backgroundHeight / 2)
    canvas.drawPath(firstTrianglePath, paint)

    //矩形
    val rectanglePath = Path()
    rectanglePath.moveTo(rectangleFirstX, rectangleFirstY)
    rectanglePath.lineTo(rectangleSecondX, rectangleSecondY)
    rectanglePath.lineTo(rectangleThirdX, rectangleThirdY)
    rectanglePath.lineTo(rectangleEndX, rectangleEndY)
    rectanglePath.close()
    canvas.rotate(-3 * (absoluteRandomNumber % 50).toFloat() - 45, backgroundWidth / 2, backgroundHeight / 2)
    canvas.drawPath(rectanglePath, paint)

    canvas.rotate((absoluteRandomNumber % 50).toFloat(), backgroundWidth / 2, backgroundHeight / 2)
    canvas.drawBitmap(foreground, ((backgroundWidth - foregroundWidth) / 2),
            ((backgroundHeight - foregroundHeight) / 2), null)
    canvas.save()
    canvas.restore()
    return newmap
  }

  /**
   * @date 07\12\2018 16\40
   * @author wcx
   * @description 根据资源文件获取bitmap
   */
  private fun convertDrawable2BitmapByCanvas(drawable: Drawable): Bitmap {
    val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, drawable.intrinsicWidth,
            drawable.intrinsicHeight)
    drawable.draw(canvas)
    return bitmap
  }

}