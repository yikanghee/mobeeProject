import axios from "axios";
import { getCookie } from "../shared/cookie";

const BASE_URL = `localhost:8080`;

export const instance = axios.create({
  // 기본적으로 우리가 바라볼 서버의 주소
  baseURL: BASE_URL,
  headers: {
    "Content-type": "application/json;charset=UTF-8",
    accept: "application/json",
    "Access-Control-Allow-Origin": "*",
  },
})

// // 요청 then catch 전에 인터셉터(가로채기) 가로채서 토큰이 있을 경우 저장해줌

instance.interceptors.request.use((config) => {

  const token = localStorage.getItem("is_token")
  const refresh = localStorage.getItem("is_refresh")
  console.log("token 인식하네?")

  if (token) {
    config.headers.common["X-AUTH-TOKEN"] = token
    config.headers.common["X-AUTH-REFRESHTOKEN"] = refresh
  } else {
    config.headers.common["X-AUTH-TOKEN"] = null
    config.headers.common["X-AUTH-REFRESHTOKEN"] = null
  }
  return config
})