# TemptTonic

TemptTonic is a sleek Android weather app built with Kotlin and Retrofit, using the OpenWeatherMap API to provide real-time weather updates.

## Features
- Real-time weather updates for any city.
- Displays current, maximum, and minimum temperatures.
- Shows humidity, wind speed, sunrise, and sunset times.
- Dynamic animations and backgrounds that change with weather conditions.
- Simple search functionality to find weather information by city.

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/TemptTonic.git
    ```

2. Open the project in Android Studio.

3. Add your OpenWeatherMap API key in the `fetchWeatherData` method:
    ```kotlin
    val response = retrofit.getWeatherData(cityName, "YOUR_API_KEY", "metric")
    ```

4. Build and run the app on an emulator or physical device.

## Usage
- Open the app and view the weather for a default city.
- Use the search bar to find weather information for different cities.

## Screenshots
- See [screenshots](screenshots/README.md) for UI previews.

## API Key Configuration
To use the app, you need an API key from OpenWeatherMap. Replace `"YOUR_API_KEY"` in the `fetchWeatherData` method with your own API key.

## Contributing
Contributions are welcome! Please open an issue or submit a pull request with any improvements or new features.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Contact
For questions or feedback, feel free to reach out at [amananand0019@gmail.com](mailto:amananand0019@gmail.com).
