import { ref, computed, onMounted } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

const REST_MAIN_API = "http://localhost:8080";

export const useOasisStore = defineStore(
  "oasis",
  () => {
    // 로그인 유저 정보 받아오기 (user의 id 값이 담겨 있음)
    // 로그인 상태가 아닐 때는 null이 반환됩니다.
    const loginUser = ref(null);
    const userId = ref(0);

    const setLoginUser = function (data) {
      loginUser.value = data;
    };

    const setUserId = function (data) {
      userId.value = data;
    };

    // 지도 데이터 받아오기
    const mapLocations = ref([]);
    const mapIdx = ref([]);
    const mapData = ref({});
    const newMapData = ref({});
    const mapImages = ref({});
    const mapCnt = ref({});

    const getMapLocations = function (data) {
      let locations = [];
      let idx = [];
      data.forEach((element, index) => {
        if (locations.indexOf(element.locationDetail) < 0) {
          locations.push(element.locationDetail);
          idx.push(index);
        }
      });
      mapLocations.value = locations;
      mapIdx.value = idx;
    };

    const getNewMapData = function (data) {
      let mapData = [];
      let newData = {
        강남: [],
        광교: [],
        서촌: [],
        북촌: [],
        삼산: [],
        상무지구: [],
        성수: [],
        신촌: [],
        압구정: [],
        양재천: [],
        어진동: [],
        을왕리: [],
        이대: [],
        종로: [],
        청주: [],
        판교: [],
        합정: [],
        해운대: [],
        홍대: [],
        신사: [],
        여의도: [],
        잠실: [],
        청담: [],
        한남: [],
        광안리: [],
        기장: [],
        전포: [],
        부평: [],
        인천공항: [],
        대전역: [],
        둔산동: [],
        성심당: [],
        동성로: [],
        안지랑곱창골목: [],
        이월드: [],
        간절곶: [],
        성남동: [],
        태화강: [],
        광주송정역: [],
        펭귄마을: [],
        서귀포: [],
        애월: [],
        우도: [],
        제주공항: [],
        강릉: [],
        속초: [],
        양양: [],
        춘천: [],
        평창: [],
        강화도: [],
        판교: [],
        분당: [],
        수원: [],
        야탑: [],
        의정부: [],
        파주: [],
        포항: [],
        경주: [],
        안동: [],
        영덕: [],
        영양: [],
        울진: [],
        거제: [],
        김해: [],
        창원: [],
        통영: [],
        군산: [],
        익산: [],
        전주: [],
        목포: [],
        여수: [],
        충주: [],
        공주: [],
        당진: [],
        세종: [],
      };
      let images = {
        간절곶: "",
        강남: "",
        광교: "",
        서촌: "",
        강릉: "",
        강화도: "",
        거제: "",
        경주: "",
        공주: "",
        광안리: "",
        광주송정역: "",
        군산: "",
        기장: "",
        김해: "",
        당진: "",
        대전역: "",
        동성로: "",
        둔산동: "",
        목포: "",
        부평: "",
        북촌: "",
        분당: "",
        삼산: "",
        상무지구: "",
        서귀포: "",
        성남동: "",
        성수: "",
        성심당: "",
        세종: "",
        속초: "",
        수원: "",
        신사: "",
        신촌: "",
        안동: "",
        안지랑곱창골목: "",
        압구정: "",
        애월: "",
        야탑: "",
        양양: "",
        양재천: "",
        어진동: "",
        여수: "",
        여의도: "",
        영덕: "",
        영양: "",
        우도: "",
        울진: "",
        을왕리: "",
        의정부: "",
        이대: "",
        이월드: "",
        익산: "",
        인천공항: "",
        잠실: "",
        전주: "",
        전포: "",
        제주공항: "",
        종로: "",
        창원: "",
        청담: "",
        청주: "",
        춘천: "",
        충주: "",
        태화강: "",
        통영: "",
        파주: "",
        판교: "",
        펭귄마을: "",
        평창: "",
        포항: "",
        한남: "",
        합정: "",
        해운대: "",
        홍대: "",
      };
      let cnt = {
        간절곶: 0,
        강남: 0,
        광교: 0,
        서촌: 0,
        강릉: 0,
        강화도: 0,
        거제: 0,
        경주: 0,
        공주: 0,
        광안리: 0,
        광주송정역: 0,
        군산: 0,
        기장: 0,
        김해: 0,
        당진: 0,
        대전역: 0,
        동성로: 0,
        둔산동: 0,
        목포: 0,
        부평: 0,
        북촌: 0,
        분당: 0,
        삼산: 0,
        상무지구: 0,
        서귀포: 0,
        성남동: 0,
        성수: 0,
        성심당: 0,
        세종: 0,
        속초: 0,
        수원: 0,
        신사: 0,
        신촌: 0,
        안동: 0,
        안지랑곱창골목: 0,
        압구정: 0,
        애월: 0,
        야탑: 0,
        양양: 0,
        양재천: 0,
        어진동: 0,
        여수: 0,
        여의도: 0,
        영덕: 0,
        영양: 0,
        우도: 0,
        울진: 0,
        을왕리: 0,
        의정부: 0,
        이대: 0,
        이월드: 0,
        익산: 0,
        인천공항: 0,
        잠실: 0,
        전주: 0,
        전포: 0,
        제주공항: 0,
        종로: 0,
        창원: 0,
        청담: 0,
        청주: 0,
        춘천: 0,
        충주: 0,
        태화강: 0,
        통영: 0,
        파주: 0,
        판교: 0,
        펭귄마을: 0,
        평창: 0,
        포항: 0,
        한남: 0,
        합정: 0,
        해운대: 0,
        홍대: 0,
      };
      let step;
      for (step = 0; step < mapIdx.value.length; step++) {
        let arr = data.slice(mapIdx.value[step], mapIdx.value[step + 1]);
        mapData.push(arr);

        if (step == mapIdx.value.length - 1) {
          cnt[mapData[step][0].locationDetail] =
            data.length - mapIdx.value[step];
        } else {
          cnt[mapData[step][0].locationDetail] =
            mapIdx.value[step + 1] - mapIdx.value[step];
        }
        images[mapData[step][0].locationDetail] = mapData[step][0].imagePath;
      }

      data.forEach((d) => {
        newData[d.locationDetail].push(d);
      });

      mapImages.value = images;
      mapCnt.value = cnt;
      return newData;
    };

    const getMapData = function () {
      axios({
        url: REST_MAIN_API,
        method: "GET",
      })
        .then((res) => {
          mapData.value = res.data.mapData;
          getMapLocations(res.data.mapData);
          newMapData.value = getNewMapData(res.data.mapData);
        })
        .catch((err) => {
          console.log(err);
        });
    };

    // 인기 게시글 데이터 받아오기
    const top3Data = ref([]);
    const top3Idx = ref([]);

    const getTop3Locations = function (data) {
      const locations = [];
      const idx = [];
      data.forEach((element, index) => {
        if (locations.indexOf(element.locationDetail) < 0) {
          locations.push(element.locationDetail);
          idx.push(index);
        }
      });
      top3Idx.value = idx;
    };

    const getNewTop3Data = function (data) {
      const newData = [];
      let step;
      for (step = 0; step < top3Idx.value.length; step++) {
        let arr = data.slice(top3Idx.value[step], top3Idx.value[step + 1]);
        newData.push(arr);
      }
      return newData;
    };

    const getTop3Data = function () {
      axios({
        url: REST_MAIN_API,
        method: "GET",
      })
        .then((res) => {
          getTop3Locations(res.data.top3Data);
          top3Data.value = getNewTop3Data(res.data.top3Data);
        })
        .catch((err) => {
          console.log(err);
        });
    };

    return {
      loginUser,
      setLoginUser,
      userId,
      setUserId,

      mapLocations,
      mapIdx,
      mapData,
      newMapData,
      mapImages,
      mapCnt,
      getMapData,

      top3Data,
      top3Idx,
      getTop3Data,
    };
  },
  { persist: true }
);
