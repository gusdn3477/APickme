import { useState, useEffect } from "react";
import { useHistory } from "react-router";
import { Link } from "react-router-dom";
import Brand from "../../elements/widgets/brand/Brand";

// 슈퍼 인사담당자 가입
export default function HRRegister() {

  const gogo = useHistory();

  const [values, setValues] = useState({
    email: '',
    name: '',
    password: '',
    confirmPassword: '',
    companyName: '',
  })

  const [guideTxts, setGuideTxts] = useState({
    emailGuide: '이메일 형식에 맞게 작성해 주세요.',
    pwdGuide: '숫자와 문자를 조합해서 최소 8글자는 입력해 주세요.',
    confirmPwdGuide: '한번더 입력해 주세요.',
    nameGuide: '',
    companyName: '',
  });

  const [error, setError] = useState({
    emailError: '',
    pwdError: '',
    confirmPwd: '',
    nameError: '',
    companyNameError: ''
  })

  const isEmail = email => {
    const emailRegex = /^(([^<>()\].,;:\s@"]+(\.[^<>()\].,;:\s@"]+)*)|(".+"))@(([^<>()¥[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;

    return emailRegex.test(email);
  };

  const isPwd = pass => {
    const pwdRegex = /^.*(?=.{6,40})(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@$!%*#?&-]).*$/;
    return pwdRegex.test(pass);
  }

  const confirmPassword = (pass, confirmPass) => {
    return pass === confirmPass
  }

  const checkEmail = () => {
    fetch(`/hr-service/hr/checkemail`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email : values.email
      }),
    }).
    then(res => res.text()).
    then(res => {
      if(res === "true"){
        alert("이미 등록된 계정입니다.")
      }
      else{
        alert("사용 가능한 이메일입니다.");
      }
    })
  }

  const onTextCheck = () => {
    let emailError = "";
    let pwdError = "";
    let confirmPwd = "";
    let nameError = "";
    let companyNameError = "";


    if (!isEmail(values.email)) emailError = "email 형식이 아닙니다.";
    if (!isPwd(values.password)) pwdError = "비밀번호 조건을 만족 할 수 없습니다.";
    if (!confirmPassword(values.password, values.confirmPassword)) confirmPwd = "비밀번호가 일치하지 않습니다.";
    if (values.userId === values.password) pwdError = "아이디를 비밀번호로 사용 할 수 없습니다.";

    if (values.name.length === 0) nameError = "이름을 입력해주세요.";
    if (values.companyName.length === 0) companyNameError = "회사명을 입력해주세요.";

    setError({
      emailError, pwdError, confirmPwd, nameError, companyNameError
    })

    if (emailError || pwdError || confirmPwd || nameError || companyNameError) return false;
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
    if (!valid) alert("올바른 정보를 입력해 주세요");
    else {
      fetch(`/hr-service/hr/request`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: values.email,
          name: values.name,
          pwd: values.password,
          corpName: values.companyName // 추후에 중복 체크 있으면 좋을 듯
        }),
      }).
        then(
          alert("회원가입에 성공하였습니다."),
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
            <div class="col-lg-4 mx-auto">
              <div class="auth-form-light text-left py-5 px-4 px-sm-5">
                <Brand/>
                <h4>슈퍼 인사담당자 회원가입</h4>
                <h6 class="font-weight-light">최소한의 정보로 가입해 보세요.</h6>
                <form class="pt-3" onSubmit={handlePutUserLists}>
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
                  <button class="btn btn-outline-success" type="button" style={{marginBottom:"1rem", marginTop:"-1rem",padding:"0.7rem", float:"right"}} onClick={checkEmail}>이메일 중복 확인</button>
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="exampleInputEmail1" 
                    name="companyName"
                    value={values.companyName}
                    onChange={handleChangeForm}
                    placeholder="기업명" />
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
                    <button type="submit" className="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">가입하기</button>
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