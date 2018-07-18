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
class RandomGeneratorImage {

    /**
     * @data 07\12\2018
     * @author wcx
     * @description 根据输入数字随机生成图片,图片集合为空则使用默认图片
     */
    fun generateImage(randomNumber: Int, context: Context, resourceDrawable: ArrayList<Int>?): Bitmap? {
        var imageArrayList = ArrayList<Int>()
        if (resourceDrawable == null || resourceDrawable.size == 0) {
            val ic_brown_bear = R.drawable.ic_brown_bear
            val ic_cattle = R.drawable.ic_cattle
            val ic_chicken = R.drawable.ic_chicken
            val ic_eagle = R.drawable.ic_eagle
            val ic_elephant = R.drawable.ic_elephant
            val ic_elk = R.drawable.ic_elk
            val ic_fox = R.drawable.ic_fox
            val ic_frogrita = R.drawable.ic_frogrita
            val ic_giraffe = R.drawable.ic_giraffe
            val ic_hippo = R.drawable.ic_hippo
            val ic_jaguar = R.drawable.ic_jaguar
            val ic_koala = R.drawable.ic_koala
            val ic_lion = R.drawable.ic_lion
            val ic_orangutan = R.drawable.ic_orangutan
            val ic_owl = R.drawable.ic_owl
            val ic_penguin = R.drawable.ic_penguin
            val ic_rabbit = R.drawable.ic_rabbit
            val ic_raccoon = R.drawable.ic_raccoon
            val ic_rhinoceros = R.drawable.ic_rhinoceros
            val ic_wolf = R.drawable.ic_wolf
            imageArrayList.add(ic_brown_bear)
            imageArrayList.add(ic_cattle)
            imageArrayList.add(ic_chicken)
            imageArrayList.add(ic_eagle)
            imageArrayList.add(ic_elephant)
            imageArrayList.add(ic_elk)
            imageArrayList.add(ic_fox)
            imageArrayList.add(ic_frogrita)
            imageArrayList.add(ic_giraffe)
            imageArrayList.add(ic_hippo)
            imageArrayList.add(ic_jaguar)
            imageArrayList.add(ic_koala)
            imageArrayList.add(ic_lion)
            imageArrayList.add(ic_orangutan)
            imageArrayList.add(ic_owl)
            imageArrayList.add(ic_penguin)
            imageArrayList.add(ic_rabbit)
            imageArrayList.add(ic_raccoon)
            imageArrayList.add(ic_rhinoceros)
            imageArrayList.add(ic_wolf)
        } else {
            imageArrayList = resourceDrawable
        }

        val imageMaxSize = imageArrayList.size
        val absoluteRandomNumber = Math.abs(randomNumber)
        // 取模
        val modulusNumber = absoluteRandomNumber % imageMaxSize

        val randomColor = getRandomColor(absoluteRandomNumber * 30, imageMaxSize) // 获得背景颜色

        var iconDrawable = ContextCompat.getDrawable(context, imageArrayList.get(modulusNumber))

        val frontBitmap = convertDrawable2BitmapByCanvas(iconDrawable!!)

        val backgroundBitmap = Bitmap.createBitmap(iconDrawable!!.intrinsicWidth, iconDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        backgroundBitmap.eraseColor(Color.parseColor(randomColor))

        val combineBitmap = combineBitmap(backgroundBitmap, frontBitmap, absoluteRandomNumber)

        return combineBitmap
    }

    /**
     * @data 07\16\2018 10\08
     * @author wcx
     */
    private fun getRandomColor(absoluteRandomNumber: Int, imageMaxSize: Int): String {

        val colorAll = 171 * 6
        val colorMax = 220;
        val colorMin = 84
        //取模
        val modulusNumber = absoluteRandomNumber % colorAll

        var redNextInt = colorMax;
        var greedNextInt = colorMin;
        var blueNextInt = colorMin;

        val colorArea = modulusNumber / 171
        val colorChangeAmount = modulusNumber % 171

        if (colorArea == 0) {
            greedNextInt += colorChangeAmount
        } else if (colorArea == 1) {
            redNextInt -= colorChangeAmount
            greedNextInt = colorMax
        } else if (colorArea == 2) {
            redNextInt = colorMin
            greedNextInt = colorMax
            blueNextInt += colorChangeAmount
        } else if (colorArea == 3) {
            redNextInt = colorMin
            greedNextInt = colorMax - colorChangeAmount
            blueNextInt = colorMax
        } else if (colorArea == 4) {
            redNextInt = colorMin + colorChangeAmount
            greedNextInt = colorMin
            blueNextInt = colorMax
        } else if (colorArea == 5) {
            redNextInt = colorMax
            greedNextInt = colorMin
            blueNextInt = colorMax - colorChangeAmount
        }

        var redToHexString = Integer.toHexString(redNextInt)
        if (redToHexString.length < 2) {
            redToHexString = "0" + redToHexString
        }
        var greedToHexString = Integer.toHexString(greedNextInt)
        if (greedToHexString.length < 2) {
            greedToHexString = "0" + greedToHexString
        }
        var blueToHexString = Integer.toHexString(blueNextInt)
        if (blueToHexString.length < 2) {
            blueToHexString = "0" + blueToHexString
        }
        val color = "#" + redToHexString + greedToHexString + blueToHexString
        return color
    }

    /**
     * @data 07\12\2018 16\40
     * @author wcx
     * @description 合成图片
     */
    private fun combineBitmap(background: Bitmap?, foreground: Bitmap, absoluteRandomNumber: Int): Bitmap? {
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
        paint.setColor(Color.WHITE)
        val linearGradient = LinearGradient(backgroundWidth, 0f, backgroundWidth, backgroundHeight, Color.WHITE, Color.TRANSPARENT, Shader.TileMode.MIRROR)
        paint.setShader(linearGradient);
        paint.alpha = 100
        paint.isAntiAlias = true // 设置画笔的锯齿效果
        paint.isDither = true

        // 三角形坐标
        var firstTriangleFlaotFirstX = 0f
        var firstTriangleFlaotSecondX = 0f
        var firstTriangleFlaotEndX = 0f

        var firstTriangleFlaotFirstY = 0f
        var firstTriangleFlaotSecondY = 0f
        var firstTriangleFlaotEndY = 0f

        // 矩形坐标
        var rectangleFlaotFirstX = 0f
        var rectangleFlaotSecondX = 0f
        var rectangleFlaotThirdX = 0f
        var rectangleFlaotEndX = 0f

        var rectangleFlaotFirstY = 0f
        var rectangleFlaotSecondY = 0f
        var rectangleFlaotThirdY = 0f
        var rectangleFlaotEndY = 0f

        val radius = backgroundWidth

        // 获得起始点所在区域
        val firstArea = (absoluteRandomNumber / 50) % 4

        val sin = Math.sin(Math.toRadians(20.toDouble()))
        val cos = Math.cos(Math.toRadians(20.toDouble()))

        var cosWidth = (radius * cos).toFloat()
        var sinHeight = (radius * sin).toFloat()

        val sinMobile = sinHeight / 2
        val speedMultiple = 11
        val coordinateMultiple = 8
        if (firstArea == 0) {
            // 三角坐标
            firstTriangleFlaotFirstX = backgroundWidth / 2 - cosWidth
            firstTriangleFlaotFirstY = backgroundHeight / 2 - sinHeight - sinMobile

            firstTriangleFlaotSecondX = backgroundWidth / 2 + cosWidth
            firstTriangleFlaotSecondY = backgroundHeight / 2 + sinHeight - sinMobile

            firstTriangleFlaotEndX = backgroundWidth / 2 - cosWidth
            firstTriangleFlaotEndY = backgroundHeight / 2 + sinHeight - sinMobile

            firstTriangleFlaotFirstY -= absoluteRandomNumber % 50 * speedMultiple / 2
            firstTriangleFlaotEndY += absoluteRandomNumber % 50 * speedMultiple

            // 四边形坐标
            rectangleFlaotFirstX = backgroundWidth / 2 - backgroundWidth / coordinateMultiple
            rectangleFlaotFirstY = backgroundHeight / 2 - cosWidth

            rectangleFlaotSecondX = backgroundWidth / 2 + backgroundWidth / coordinateMultiple
            rectangleFlaotSecondY = backgroundHeight / 2 - cosWidth

            rectangleFlaotThirdX = backgroundWidth / 2 + backgroundWidth / coordinateMultiple
            rectangleFlaotThirdY = backgroundHeight / 2 + cosWidth

            rectangleFlaotEndX = backgroundWidth / 2 - backgroundWidth / coordinateMultiple
            rectangleFlaotEndY = backgroundHeight / 2 + cosWidth

            rectangleFlaotThirdX += absoluteRandomNumber % 50 * speedMultiple * 2
            rectangleFlaotEndX -= absoluteRandomNumber % 50 * speedMultiple * 2
        } else if (firstArea == 1) {
            // 三角坐标
            firstTriangleFlaotFirstX = backgroundWidth / 2 - sinHeight + sinMobile
            firstTriangleFlaotFirstY = backgroundHeight / 2 - cosWidth

            firstTriangleFlaotSecondX = backgroundWidth / 2 + sinHeight + sinMobile
            firstTriangleFlaotSecondY = backgroundHeight / 2 + cosWidth

            firstTriangleFlaotEndX = backgroundWidth / 2 - sinHeight + sinMobile
            firstTriangleFlaotEndY = backgroundHeight / 2 + cosWidth

            firstTriangleFlaotSecondX += absoluteRandomNumber % 50 * speedMultiple / 2
            firstTriangleFlaotEndX -= absoluteRandomNumber % 50 * speedMultiple

            // 四边形坐标
            rectangleFlaotFirstX = backgroundWidth / 2 - cosWidth
            rectangleFlaotFirstY = backgroundHeight / 2 - backgroundWidth / coordinateMultiple

            rectangleFlaotSecondX = backgroundWidth / 2 + cosWidth
            rectangleFlaotSecondY = backgroundHeight / 2 - backgroundWidth / coordinateMultiple

            rectangleFlaotThirdX = backgroundWidth / 2 + cosWidth
            rectangleFlaotThirdY = backgroundHeight / 2 + backgroundWidth / coordinateMultiple

            rectangleFlaotEndX = backgroundWidth / 2 - cosWidth
            rectangleFlaotEndY = backgroundHeight / 2 + backgroundWidth / coordinateMultiple

            rectangleFlaotSecondY -= absoluteRandomNumber % 50 * speedMultiple * 2
            rectangleFlaotThirdY += absoluteRandomNumber % 50 * speedMultiple * 2
        } else if (firstArea == 2) {
            // 三角坐标
            firstTriangleFlaotFirstX = backgroundWidth / 2 + cosWidth
            firstTriangleFlaotFirstY = backgroundHeight / 2 - sinHeight - sinMobile

            firstTriangleFlaotSecondX = backgroundWidth / 2 + cosWidth
            firstTriangleFlaotSecondY = backgroundHeight / 2 + sinHeight - sinMobile

            firstTriangleFlaotEndX = backgroundWidth / 2 - cosWidth
            firstTriangleFlaotEndY = backgroundHeight / 2 + sinHeight - sinMobile

            firstTriangleFlaotFirstY -= absoluteRandomNumber % 50 * speedMultiple / 2
            firstTriangleFlaotSecondY += absoluteRandomNumber % 50 * speedMultiple

            // 四边形坐标
            rectangleFlaotFirstX = backgroundWidth / 2 - backgroundWidth / coordinateMultiple
            rectangleFlaotFirstY = backgroundHeight / 2 - cosWidth

            rectangleFlaotSecondX = backgroundWidth / 2 + backgroundWidth / coordinateMultiple
            rectangleFlaotSecondY = backgroundHeight / 2 - cosWidth

            rectangleFlaotThirdX = backgroundWidth / 2 + backgroundWidth / coordinateMultiple
            rectangleFlaotThirdY = backgroundHeight / 2 + cosWidth

            rectangleFlaotEndX = backgroundWidth / 2 - backgroundWidth / coordinateMultiple
            rectangleFlaotEndY = backgroundHeight / 2 + cosWidth

            rectangleFlaotFirstX -= absoluteRandomNumber % 50 * speedMultiple * 2
            rectangleFlaotSecondX += absoluteRandomNumber % 50 * speedMultiple * 2
        } else if (firstArea == 3) {
            // 三角坐标
            firstTriangleFlaotFirstX = backgroundWidth / 2 + sinHeight - sinMobile
            firstTriangleFlaotFirstY = backgroundHeight / 2 + cosWidth

            firstTriangleFlaotSecondX = backgroundWidth / 2 - sinHeight - sinMobile
            firstTriangleFlaotSecondY = backgroundHeight / 2 - cosWidth

            firstTriangleFlaotEndX = backgroundWidth / 2 + sinHeight - sinMobile
            firstTriangleFlaotEndY = backgroundHeight / 2 - cosWidth

            firstTriangleFlaotSecondX -= absoluteRandomNumber % 50 * speedMultiple / 2
            firstTriangleFlaotEndX += absoluteRandomNumber % 50 * speedMultiple

            // 四边形坐标
            rectangleFlaotFirstX = backgroundWidth / 2 - cosWidth
            rectangleFlaotFirstY = backgroundHeight / 2 - backgroundWidth / coordinateMultiple

            rectangleFlaotSecondX = backgroundWidth / 2 + cosWidth
            rectangleFlaotSecondY = backgroundHeight / 2 - backgroundWidth / coordinateMultiple

            rectangleFlaotThirdX = backgroundWidth / 2 + cosWidth
            rectangleFlaotThirdY = backgroundHeight / 2 + backgroundWidth / coordinateMultiple

            rectangleFlaotEndX = backgroundWidth / 2 - cosWidth
            rectangleFlaotEndY = backgroundHeight / 2 + backgroundWidth / coordinateMultiple

            rectangleFlaotFirstY -= absoluteRandomNumber % 50 * speedMultiple * 2
            rectangleFlaotEndY += absoluteRandomNumber % 50 * speedMultiple * 2
        }

        // 第一个三角形
        val firstTrianglePath = Path()
        firstTrianglePath.moveTo(firstTriangleFlaotFirstX, firstTriangleFlaotFirstY)
        firstTrianglePath.lineTo(firstTriangleFlaotSecondX, firstTriangleFlaotSecondY)
        firstTrianglePath.lineTo(firstTriangleFlaotEndX, firstTriangleFlaotEndY)
        firstTrianglePath.close()
        canvas.rotate((absoluteRandomNumber % 50).toFloat(), backgroundWidth / 2, backgroundHeight / 2)
        canvas.drawPath(firstTrianglePath, paint)

        // 第二个三角形
        canvas.rotate((absoluteRandomNumber % 50).toFloat() + 45, backgroundWidth / 2, backgroundHeight / 2)
        canvas.drawPath(firstTrianglePath, paint)

        //矩形
        val rectanglePath = Path()
        rectanglePath.moveTo(rectangleFlaotFirstX, rectangleFlaotFirstY)
        rectanglePath.lineTo(rectangleFlaotSecondX, rectangleFlaotSecondY)
        rectanglePath.lineTo(rectangleFlaotThirdX, rectangleFlaotThirdY)
        rectanglePath.lineTo(rectangleFlaotEndX, rectangleFlaotEndY)
        rectanglePath.close()
        canvas.rotate(-3 * (absoluteRandomNumber % 50).toFloat() - 45, backgroundWidth / 2, backgroundHeight / 2)
        canvas.drawPath(rectanglePath, paint)

        canvas.rotate((absoluteRandomNumber % 50).toFloat(), backgroundWidth / 2, backgroundHeight / 2)
        canvas.drawBitmap(foreground, ((backgroundWidth - foregroundWidth) / 2).toFloat(),
                ((backgroundHeight - foregroundHeight) / 2).toFloat(), null)
        canvas.save()
        canvas.restore()
        return newmap
    }

    /**
     * @data 07\12\2018 16\40
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