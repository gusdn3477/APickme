const CLIENT_ID = "50ce915f6d38eea17423604861405eec";
const REDIRECT_URI =  "http://localhost:3000/login/oauth2/callback/kakao";

export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;