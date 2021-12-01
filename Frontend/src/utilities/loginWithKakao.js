import { Button } from "bootstrap";
import { useHistory } from "react-router";
import { KAKAO_AUTH_URL } from "./Oauth";

const {Kakao} = window;

function SocialLogin() {
    
    const history = useHistory();
    const kakaoLoginHandler = () => {
        Kakao.Auth.login({
            success: function(authObj){
                fetch(`${KAKAO_AUTH_URL}`, {
                    method: "POST",
                    body: JSON.stringify({
                        access_token: authObj.access_token
                    })
                })
                .then(res => res.json())
                .then(res => {
                    localStorage.setItem("Kakao_token", res.access_token);
                    if(res.access_token){
                        alert("Wanna One에 오신 걸 환영합니다!");
                        history.push("/login");
                    }
                })
            },
            fail: function(err){
                alert(JSON.stringify(err))
            }
        })
    }
}

return (
    <div><button onClick={SocialLogin}/></div>
)