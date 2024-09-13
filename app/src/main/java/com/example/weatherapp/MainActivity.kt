package com.example.weatherapp

//import android.app.Activity
//import android.content.ContentValues.TAG
import android.os.Bundle
//import android.provider.ContactsContract.Data
//import android.util.Log
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherapp.databinding.ActivityMainBinding
//import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
//import java.util.Objects

//18226018d16dbcfd43d19e161e44611c
//{"coord":{"lon":85.1167,"lat":25.6},"weather":[{"id":701,"main":"Mist","description":"mist","icon":"50n"}],"base":"stations","main":{"temp":302.11,"feels_like":309.11,"temp_min":302.11,"temp_max":302.11,"pressure":1004,"humidity":94,"sea_level":1004,"grnd_level":997},"visibility":3000,"wind":{"speed":2.06,"deg":80},"clouds":{"all":75},"dt":1724962297,"sys":{"type":1,"id":9129,"country":"IN","sunrise":1724975961,"sunset":1725021683},"timezone":19800,"id":1260086,"name":"Patna","cod":200}
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fetchWeatherData("patna")
        searchCity()
    }

    private fun searchCity() {
        val searchView= binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun fetchWeatherData(cityName: String) {
        val retrofit= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)
        val response= retrofit.getWeatherData(cityName,"18226018d16dbcfd43d19e161e44611c","metric")
        response.enqueue(object : Callback<WeatherApp>{
            override fun onResponse(p0: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody= response.body()
                if (response.isSuccessful && responseBody!=null){
                    val temperaturep= responseBody.main.temp.toString()
                    val humidity=responseBody.main.humidity
                    val windSpeed= responseBody.wind.speed
                    val sunrise = responseBody.sys.sunrise.toLong()
                    val sunset = responseBody.sys.sunset.toLong()
                    val seaLevel= responseBody.main.pressure
                    val condition= responseBody.weather.firstOrNull()?.main?:"unknown"
                    val maxtemp= responseBody.main.temp_max
                    val mintemp= responseBody.main.temp_min
                    binding.tempt.text= "$temperaturep °C"
                    binding.weather.text=condition
                    binding.maxTempt.text= "Max Temp: $maxtemp °C"
                    binding.minTempt.text= "Min Temp: $mintemp °C"
                    binding.humidity.text="$humidity %"
                    binding.windSpeed.text="$windSpeed m/s"
                    binding.sunrise.text="${time(sunrise)}"
                    binding.sunset.text="${time(sunset)}"
                    binding.sea.text="$seaLevel hpa"
                    binding.condition.text= condition
                    binding.day.text= dayName(System.currentTimeMillis())
                    binding.date.text= date()
                    binding.cityName.text="$cityName"
//                    Log.d("TAG", "onResponse: $temperaturep")
                    changrImagesAccordingToWeatherCondition(condition)
                }
            }

            override fun onFailure(p0: Call<WeatherApp>, p1: Throwable) {

            }

        })
    }

    private fun changrImagesAccordingToWeatherCondition(conditons: String) {
        when(conditons){
            "Clear Sky","Sunny","Clear"->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
            "Overcast","Mist","Foggy"->{
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)}
            "Partly Clouds","Clouds"->{
                binding.root.setBackgroundResource(R.drawable.bright_backgrouund)
                binding.lottieAnimationView.setAnimation(R.raw.clouds)
            }
            "Light Rain","Drizzle","Moderate Rain","Showers","Heavy Rain","Rain"->{
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView.setAnimation(R.raw.rain)
            }
            "Light Snow","Moderate Snow","Heavy Snow","Blizzard"->{
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView.setAnimation(R.raw.snow)
            }
            else->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
        }
    binding.lottieAnimationView.playAnimation()
    }

    fun date(): String {
        val sdf = SimpleDateFormat ("dd MMMM yyyy", Locale.getDefault())
        return sdf.format((Date()))

    }
    fun time(timestamp: Long): String {
        val sdf = SimpleDateFormat ("HH:mm", Locale.getDefault())
        return sdf.format((Date(timestamp*1000)))

    }

    fun dayName(timestamp: Long): String{
        val sdf = SimpleDateFormat ("EEEE", Locale.getDefault())
        return sdf.format((Date()))
    }
}