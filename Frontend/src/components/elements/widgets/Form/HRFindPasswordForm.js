import { useState } from "react";
import { useHistory } from "react-router";
import Brand from "../brand/Brand";

export default function HRFindPasswordForm() {

  const gogo = useHistory();
  const [usersDatas, setUsersDatas] = useState([]);
  const [values, setValues] = useState({
    email: '',
    name: ''
  })

  const [guideTxts, setGuideTxts] = useState({
    email: '',
    name: ''
  });

  const [error, setError] = useState({
    pwdError: '',
    confirmPwd: ''
  })

  const onTextCheck = () => {
    if (values.name.length === 0) {
      return false;
    }
    return true;
  }

  const handleChangeForm = (e) => {
    setValues({
      ...values,
      [e.target.name]: e.target.value
    });
  }

  const handlePutUserLists = (e) => {

    e.preventDefault();
    const valid = onTextCheck();
    if (!valid) alert("정확한 정보를 입력해 주세요.");
    else {
      fetch(`/hr-service/hr/findpwd`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: values.email,
          name: values.name
        }),
      }).
      then(res => 
        res.text()
      )
      .then(res => {
        if(res === "TRUE"){ // 이건 문자열 반환이라.. boolean이랑 다른 듯
          alert("이메일을 확인해 주세요.");
        }
        else{
          alert("회원정보가 일치하지 않습니다.");
        }
      })
    }
  }

  return (
    <div class="container-scroller">
      <div class="container-fluid page-body-wrapper full-page-wrapper">
        <div class="content-wrapper d-flex align-items-center auth px-0">
          <div class="row w-100 mx-0">
            <div class="col-lg-4 mx-auto">
              <div class="auth-form-light text-left py-5 px-4 px-sm-5">
                <Brand />
                <h4>비밀번호 확인</h4>
                <h6 class="font-weight-light">비밀번호가 일치해야 회원정보 수정 페이지로 넘어갈 수 있습니다.</h6>
                <form class="pt-3" onSubmit={handlePutUserLists}>
                  <div class="form-group">
                    <input type="email" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="이메일"
                      name="email"
                      value={values.email}
                      onChange={handleChangeForm} />
                  </div>

                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="이름"
                      name="name"
                      value={values.name}
                      onChange={handleChangeForm} />
                  </div>

                  <div class="mt-3">
                    <button type="submit" class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">확인</button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}