package id.kotlin.volumebalok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.NumberFormatException

//MainActivity inherit ke superclass bernama AppCompatActivity.
class MainActivity : AppCompatActivity(), View.OnClickListener { //View.OnClickListener adalah listener yang kita implementasikan untuk memantau kejadian klik pada komponen tombol(button)

    //Deklarasi semua komponen view
    private lateinit var edtWidth: EditText
    private lateinit var edtHeight: EditText
    private lateinit var edtLength: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //MainActivity atau kelas ini akan menampilkan tampilan yg berasal dari layout activity_main.xml

        //Casting view
        edtLength = findViewById(R.id.edt_length) //EditText edtLength disesuaikan(cast) dengan komponen EditText ber-ID edt_length di layout activity_main.xml melalui metode findViewById()
        edtWidth = findViewById(R.id.edt_width)
        edtHeight = findViewById(R.id.edt_height)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)

        btnCalculate.setOnClickListener (this) //Memasang event click listener untuk objek btnCalculate agar berfungsi ketika diklik, maka fungsi onClick() akan dipanggil dan melakukan proses yang ada di dalamnya

        //disinilah nilai bundle yg disimpan pada onSaceInstanceState akan digunakan.
        if(savedInstanceState != null){
            val result = savedInstanceState.getString(STATE_RESULT) as String
            tvResult.text = result
        }
    }

    companion object{
        private const val STATE_RESULT = "state_result"
    }

    //untuk menyimpan data sebelum activity hancur pada bundle menggunakan konsep KEY-VALUE, STATE_RESULT sebagai KEY dan tvResult sebagai VALUE
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, tvResult.text.toString())
    }

    override fun onClick(v: View) {
        if(v.id == R.id.btn_calculate){
            val inputLength = edtLength.text.toString().trim() //.text.toString() berfungsi untuk mengambil isi dari sebuah EditText() dan disimpan di variabel. .trim() berfungsi menghiraukan spasi jika ada
            val inputWidth = edtWidth.text.toString().trim()
            val inputHeight = edtHeight.text.toString().trim()

            //Mengecek inputan yang kosong
            var isEmptyFields = false
            var isInvalidDouble = false

            if(inputLength.isEmpty()){ //.isEmpty() berfungsi untuk mengecek apakah inputan dari EditText itu masing kosong.
                isEmptyFields = true //Jika true maka akan menampilkan pesan error
                edtLength.error = "Field ini tidak boleh kosong"
            }

            if(inputWidth.isEmpty()){
                isEmptyFields = true
                edtWidth.error = "Field ini tidak boleh kosong"
            }

            if(inputHeight.isEmpty()){
                isEmptyFields = true
                edtHeight.error = "Field ini tidak boleh kosong"
            }

            //validasi input yang bukan angka
            val length = toDouble(inputLength) //toDouble adalah fungsi yang kita buat sendiri yang ada di bawah, berada diluar onCreate()
            val width = toDouble(inputWidth) //toDouble untuk merubah yang sebelumnya String menjadi Double untuk melakukan proses perhitungan. Karena default input pada EditText berupa String
            val height = toDouble(inputHeight)

            if(length == null){
                isInvalidDouble = true
                edtLength.error = "Field ini harus berupa nomer yang valid"
            }

            if(width == null){
                isInvalidDouble = true
                edtWidth.error = "Field ini harus berupa nomer yang valid"
            }

            if(height == null){
                isInvalidDouble = true
                edtWidth.error = "Field ini harus berupa nomer yang valid"
            }

            //menampilkan data ke EditText
            if(!isEmptyFields && !isInvalidDouble){
                val volume = length as Double * width as Double * height as Double //melakukan proses perhitungan
                tvResult.text = volume.toString() //menampilkan hasil dari perhitungan dengan merubah tipe data yang sebelumnya Double menjadi String menggunakan .toString()
            }
        }
    }

    //fungsi untuk merubah String ke Double
    private fun toDouble(str: String): Double? {
        return try {
            str.toDouble()
        } catch (e: NumberFormatException){
            null
        }

    }
}
