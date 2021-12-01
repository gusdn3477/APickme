import { useState, useEffect } from "react";
import { useHistory } from "react-router";
import { Link } from "react-router-dom";
import Brand from "../brand/Brand";

export default function UserCheckPasswordForm() {

  const gogo = useHistory();

  const [values, setValues] = useState({
    password: '',
    confirmPassword: ''
  })

  const [guideTxts, setGuideTxts] = useState({
    pwdGuide: '숫자와 문자를 조합해서 최소 8글자는 입력해 주세요.',
    confirmPwdGuide: '한번더 입력해 주세요.'
  });

  const [error, setError] = useState({
    pwdError: '',
    confirmPwd: ''
  })

  const isPwd = pass => {
    const pwdRegex = /^.*(?=.{6,40})(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@$!%*#?&-]).*$/;
    return pwdRegex.test(pass);
  }

  const confirmPassword = (pass, confirmPass) => {
    return pass === confirmPass
  }

  const onTextCheck = () => {
    let pwdError = "";
    let confirmPwd = "";


    if (!isPwd(values.password)) pwdError = "비밀번호 조건을 만족 할 수 없습니다.";
    if (!confirmPassword(values.password, values.confirmPassword)) confirmPwd = "비밀번호가 일치하지 않습니다.";
    if (values.userId === values.password) pwdError = "아이디를 비밀번호로 사용 할 수 없습니다.";

    //console.log(userIdError, emailError, pwdError, confirmPwd, nameError, phoneError, userTypesError, useConfirmError)
    setError({
      pwdError, confirmPwd
    })

    if (pwdError || confirmPwd) return false;
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

    if (!valid) console.error("retry");

    else {

      fetch(`/user-service/users/checkpwd`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userId: localStorage.getItem('userId'), // 토큰에서 가지고 있어야 함. 유저 조회 기능 넣어서 가져온 뒤 비밀번호 비교 후에 짜야 할 듯(백엔드 단에서 처리)
          password: values.password
        }),
      }).
      then(res => res.text()).
      then(res => {
        if(res === "true"){
          gogo.push('/user/edit/profile');
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
                    <input type="password" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="비밀번호"
                      name="password"
                      value={values.password}
                      onChange={handleChangeForm} />
                  </div>
                  {
                    error.pwdError
                      ?
                      <div style={{ color: "red", fontSize: "12px", margin: '-5px 0 10px 15px' }}>{error.pwdError}</div>
                      :
                      <div style={{ color: "gray", fontSize: "12px", margin: '-5px 0 10px 15px' }}>{guideTxts.pwdGuide}</div>
                  }
                  <div class="form-group">
                    <input type="password" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="비밀번호 확인"
                      name="confirmPassword"
                      value={values.confirmPassword}
                      onChange={handleChangeForm} />
                  </div>
                  {
                    error.pwdError2
                      ?
                      <div style={{ color: "red", fontSize: "12px", margin: '-5px 0 10px 15px' }}>{error.pwdError2}</div>
                      :
                      <div style={{ color: "gray", fontSize: "12px", margin: '-5px 0 10px 15px' }}>{guideTxts.confirmpwdGuide}</div>
                  }
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