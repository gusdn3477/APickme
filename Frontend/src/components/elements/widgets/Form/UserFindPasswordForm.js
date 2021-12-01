import { useState, useEffect } from "react";
import { useHistory } from "react-router";
import Brand from "../brand/Brand";

export default function UserFindPasswordForm() {

  const gogo = useHistory();

  const [values, setValues] = useState({
    email: '',
    phoneNum: ''
  })

  const onTextCheck = () => {
    if(values.email.length === 0){
      return false;
    }
    else{
      return true;
    }
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

      fetch(`/user-service/users/findpwd`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          phoneNum : values.phoneNum,
          email: values.email
        }),
      })
      .then(res => 
        res.text()
      ).then(res => {
        if(res === "TRUE"){
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
                <h4>비밀번호 찾기</h4>
                <h6 class="font-weight-light">회원정보가 일치해야 비밀번호를 재발급 받을 수 있습니다.</h6>
                <form class="pt-3" onSubmit={handlePutUserLists}>
                  <div class="form-group">
                    <input type="email" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="이메일"
                      name="email"
                      value={values.email}
                      onChange={handleChangeForm} />
                  </div>
                  
                  <div class="form-group">
                    <input type="name" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="휴대폰 번호"
                      name="phoneNum"
                      value={values.phoneNum}
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