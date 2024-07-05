<template>
  <div>
    <div id="map"></div>
  </div>
</template>

<script setup>
import { onMounted, ref, toRaw } from "vue";
import { useOasisStore } from "@/stores/oasis";
import { useRouter } from "vue-router";

const store = useOasisStore();
const router = useRouter();

onMounted(() => {
  store.getMapData();
  initMap();
});

const initMap = function () {
  const script = document.createElement("script");
  script.src = `https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${import.meta.env.VITE_NAVER_API_KEY
    }`;
  script.async = true;
  script.defer = true;
  document.head.appendChild(script);
  script.onload = () => {
    let map = new naver.maps.Map("map", {
      center: new naver.maps.LatLng(37.4966645, 127.0629804),
      zoom: 11,
    });

    var MARKER_SPRITE_POSITION = {
      강남: [37.4966645, 127.0629804],
      북촌: [37.5814696, 126.9849519],
      서촌: [37.5786683, 126.973107],
      신사: [37.5210566, 127.0228686],
      여의도: [37.5258975, 126.9284261],
      잠실: [37.5124641, 127.102543],
      청담: [37.5233931, 127.0479736],
      한남: [37.5375279, 127.0051334],
      성수: [37.5406846, 127.0566319],
      신촌: [37.5650797, 126.9399549],
      압구정: [37.5271478, 127.0334517],
      양재천: [37.4702778, 27.0308333],
      이대: [37.5571187, 126.9477505],
      종로: [37.5949159, 126.977339],
      합정: [37.5495753, 126.9139908],
      홍대: [37.5558499, 126.9241498],
      해운대: [35.1938469, 129.1536102],
      광안리: [35.1531696, 129.118666],
      기장: [35.297975, 129.2009199],
      전포: [35.1554021, 129.0638741],
      을왕리: [37.4477331, 126.3725042],
      부평: [37.4939904, 126.7231945],
      인천공항: [37.458666, 126.4419679],
      대전역: [36.332326, 127.434211],
      성심당: [36.3276832, 127.4273424],
      둔산동: [36.3519375, 127.3781875],
      안지랑곱창골목: [35.8380567, 128.5757544],
      이월드: [35.8533511, 128.5638836],
      동성로: [35.8708214, 128.5955436],
      삼산: [35.540709, 129.3406352],
      태화강: [35.5372222, 129.37],
      간절곶: [35.3590942, 129.36061],
      성남동: [35.5530003, 129.3175187],
      상무지구: [35.149991, 126.8560177],
      광주송정역: [35.1376678, 126.7900992],
      펭귄마을: [35.140536, 126.9175563],
      서귀포: [33.2541205, 126.560076],
      제주공항: [33.5070772, 126.4934311],
      애월: [33.4110784, 126.3939276],
      우도: [33.5042977, 126.954048],
      강릉: [37.7091295, 128.8324462],
      속초: [38.1905823, 128.603541],
      양양: [38.0868678, 128.6653435],
      춘천: [37.8796106, 127.7283533],
      평창: [37.6668578, 128.7066944],
      강화도: [37.7131742, 126.4511281],
      광교: [37.2830911, 127.0659215],
      수원: [37.2871202, 127.0119379],
      야탑: [37.41132, 127.128674],
      의정부: [37.7440498, 127.0494386],
      파주: [37.7888414, 126.698722],
      경주: [35.8376609, 129.209724],
      안동: [36.5683272, 128.7296112],
      영덕: [36.3593924, 129.3861343],
      영양: [36.6964131, 129.1450322],
      울진: [36.9038993, 129.3124073],
      포항: [36.0529561, 129.5552893],
      거제: [34.8809062, 128.6218472],
      김해: [35.2285453, 128.889352],
      창원: [35.2279728, 128.681882],
      통영: [34.8539374, 128.4340397],
      군산: [35.9872026, 126.7112691],
      익산: [36.0230799, 126.9894962],
      전주: [35.8175376, 127.1520417],
      목포: [34.8118351, 126.3921664],
      여수: [34.7363372, 127.749122],
      청주: [36.6272962, 127.498731],
      충주: [37.0151461, 127.8956693],
      공주: [36.446551, 127.1190325],
      당진: [36.8896494, 126.6460204],
      세종: [36.4799919, 127.2890511],
    };

    var markers = [],
      infoWindows = [];

    for (var key in MARKER_SPRITE_POSITION) {
      if (store.mapCnt[key] == null || store.mapCnt[key] == 0) continue;
      let iconContent = [
        `<div>
            <div style="width:65px; height:12px"><span style="position: absolute;
            top: 0;
            right: 0;
            z-index: 3;
            height: 25px;
            width: 25px;
            line-height: 25px;
            text-align: center;
            background-color: var(--main-color);
            border-radius: 10px;
            display: inline-block;">
            ${store.mapCnt[key]}</span></div>
            <img src="http://localhost:8080/img/${store.mapImages[key]}" style="width: 60px; object-fit:cover; box-shadow: 0 4px 5px -4px black; border-radius:10px; border: 4px solid white;"/>
            </div>`,
      ].join("");

      let markerOptions = {
        map: map,
        position: new naver.maps.LatLng(
          MARKER_SPRITE_POSITION[key][0],
          MARKER_SPRITE_POSITION[key][1]
        ),
        title: key,
        icon: {
          content: iconContent,
          size: new naver.maps.Size(50, 50),
          origin: new naver.maps.Point(0, 0),
        },
      };

      var marker = new naver.maps.Marker(markerOptions);

      let list = store.newMapData[key];
      let test = ``;
      for (var idx in list) {
        let content = `<div>
          <a href="http://localhost:5173/board/${list[idx].boardId}" style="text-decoration: none; color: black;">
          <p style="margin: 7px 5px 2px 5px; overflow:hidden; white-space: nowrap; text-overflow: ellipsis; ">${list[idx].title}</p>
          <img src="http://localhost:8080/img/${list[idx].imagePath}" 
          style="box-shadow: 0 4px 5px -4px black; 
          border-radius: 5px; 
          width: 60px;
          object-fit: cover; 
          margin-left: 18px; 
          margin-right: 18px; 
          margin-bottom: 5px;"/>
          </a>
          </div>`;
        test += content;
      }
      let infoContent = [
        `<div style="
        text-align:center; 
        background-color: white; 
        padding: 5px 0;
        border-radius: 10px; 
        border: linear-gradient(to top bottom,  gray, white);  
        "><p style="font: var(--sm-pre-font);">📍 ${key}</p>
        `,
        test,
        `</div>`,
      ].join("");
      var infoWindow = new naver.maps.InfoWindow({
        content: infoContent,
        maxWidth: 140,
        backgroundColor: "none",
        borderWidth: 0,
        anchorSize: new naver.maps.Size(20, 10),
        anchorSkew: true,
        anchorColor: "white",
        pixelOffset: new naver.maps.Point(20, -20),
      });

      markers.push(marker);
      infoWindows.push(infoWindow);
    }

    function updateMarkers(map, markers) {
      var mapBounds = map.getBounds();
      var marker, position;

      for (var i = 0; i < markers.length; i++) {
        marker = markers[i];
        position = marker.getPosition();

        if (mapBounds.hasLatLng(position)) {
          showMarker(map, marker);
        } else {
          hideMarker(map, marker);
        }
      }
    }

    function showMarker(map, marker) {
      if (marker.setMap()) return;
      marker.setMap(map);
    }

    function hideMarker(map, marker) {
      if (!marker.setMap()) return;
      marker.setMap(null);
    }

    // 해당 마커의 인덱스를 seq라는 클로저 변수로 저장하는 이벤트 핸들러를 반환합니다.
    function getClickHandler(seq) {
      return function (e) {
        var marker = markers[seq],
          infoWindow = infoWindows[seq];

        if (infoWindow.getMap()) {
          infoWindow.close();
        } else {
          infoWindow.open(map, marker);
        }
      };
    }

    for (var i = 0, ii = markers.length; i < ii; i++) {
      naver.maps.Event.addListener(markers[i], "click", getClickHandler(i));
    }
  };
};
</script>

<style scoped>
* {
  font: var(--xs-pre-font);
}

#map {
  width: 600px;
  height: 500px;
  box-shadow: 4px 4px 4px -4px black;
  border-radius: 10px;
  border: none;
}
</style>
