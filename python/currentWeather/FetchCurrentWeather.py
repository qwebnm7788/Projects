import requests

def KtoC(Kelvin):
    return Kelvin - 273.15

ipUrl = "http://ip-api.com/json"
ipResponse = requests.get(ipUrl)
ipData = ipResponse.json()

weatherUrl = "http://api.openweathermap.org/data/2.5/weather?lat=" + str(ipData['lat']) +  "&lon=" + str(ipData['lon']) + "&appid=48f885b0c1286e3fc25a557e6e665597"

weatherResponse = requests.get(weatherUrl)
weatherData = weatherResponse.json()


print(weatherData['name'], '의 현재 날씨 : ', weatherData['weather'][0]['main'])
print('현재 온도 {:.2f}℃'.format(KtoC(weatherData['main']['temp'])))
