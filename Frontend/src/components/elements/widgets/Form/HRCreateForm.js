import { useState } from "react";
import { useHistory } from "react-router";
import Brand from "../brand/Brand";

export default function HRCreateForm() {

  const gogo = useHistory();

  const [values, setValues] = useState({
    email: '',
    password: '',
    confirmPassword: '',
    name: ''
  })

  const [guideTxts, setGuideTxts] = useState({
    emailGuide: '이메일 형식에 맞게 작성해 주세요.',
    pwdGuide: '숫자와 문자를 조합해서 최소 8글자는 입력해 주세요.',
    confirmPwdGuide: '한번더 입력해 주세요.',
    nameGuide: '',
  });

  const [error, setError] = useState({
    emailError: '',
    pwdError: '',
    confirmPwd: '',
    nameError: ''
  })

  const isEmail = email => {
    const emailRegex = /^(([^<>()\].,;:\s@"]+(\.[^<>()\].,;:\s@"]+)*)|(".+"))@(([^<>()¥[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;

    return emailRegex.test(email);
  };

  const isPwd = pass => {
    const pwdRegex = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@$!%*#?&]).*$/;

    return pwdRegex.test(pass);
  }

  const confirmPassword = (pass, confirmPass) => {
    return pass === confirmPass
  }

  const onTextCheck = () => {
    let emailError = "";
    let pwdError = "";
    let confirmPwd = "";
    let nameError = "";


    if (!isEmail(values.email)) emailError = "email 형식이 아닙니다.";
    if (!isPwd(values.password)) pwdError = "비밀번호 조건을 만족 할 수 없습니다.";
    if (!confirmPassword(values.password, values.confirmPassword)) confirmPwd = "비밀번호가 일치하지 않습니다.";
    
    if (values.name.length === 0) nameError = "이름을 입력해주세요.";

    //console.log(userIdError, emailError, pwdError, confirmPwd, nameError, phoneError, userTypesError, useConfirmError)
    setError({
      emailError, pwdError, confirmPwd, nameError
    })

    if (emailError || pwdError || confirmPwd || nameError) return false;
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
      fetch(`/hr-service/hr/register`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          pwd: values.password,
          name: values.name,
          email: values.email,
          empNo: localStorage.getItem('empNo')
        }),
      }).
        then(
          alert("회원가입이 완료되었습니다."),
          gogo.push('/')
          //window.location.href = '/'
        )
    }
  }
  return (
    <div class="container-scroller">
      <div class="container-fluid page-body-wrapper full-page-wrapper">
        <div class="content-wrapper d-flex align-items-center auth px-0">
          <div class="row w-100 mx-0">
            <div class="col-lg-6 mx-auto">
              <div class="auth-form-light text-left py-5 px-4 px-sm-5">
                <Brand/>
                <h4>새로운 인사담당자를 등록해 보세요.</h4>
                <form class="pt-5" onSubmit={handlePutUserLists}>
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="exampleInputUsername1" 
                    name="name"
                    value={values.name}
                    onChange={handleChangeForm}
                    placeholder="이름" />
                  </div>
                  <div class="form-group">
                    <input type="email" class="form-control form-control-lg" id="exampleInputEmail1" 
                    name="email"
                    value={values.email}
                    onChange={handleChangeForm}
                    placeholder="이메일" />
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-lg" id="exampleInputPassword1" 
                    name="password"
                    value={values.password}
                    onChange={handleChangeForm}
                    placeholder="비밀번호" />
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-lg" id="exampleInputPassword2" 
                    name="confirmPassword"
                    value={values.confirmPassword}
                    onChange={handleChangeForm}
                    placeholder="비밀번호 확인" />
                  </div>
                  <div class="mb-4">
                    <div class="form-check">
                      <label class="form-check-label text-muted">
                        <input type="checkbox" class="form-check-input" />
                        I agree to all Terms & Conditions
                      </label>
                    </div>
                  </div>
                  <div class="mt-3">
                    <button type="submit" class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">등록하기</button>
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