// import Header from '../../layout/Header';
// import Footer from '../../layout/Footer';
// import Bread from '../../elements/ui/Bread';
// import RegisterForm from '../../elements/widgets/Form/Register';
// import { Fragment } from 'react';
import { useState, useEffect } from "react";
import { useHistory } from "react-router";
import { Link } from "react-router-dom";
import DaumPost from "../../../utilities/DaumPostCode";
import Brand from "../../elements/widgets/brand/Brand";

export default function UserRegister() {

  const [address, setAddress] = useState(''); // 주소
  const [addressDetail, setAddressDetail] = useState(''); // 상세주소
  const [isOpenPost, setIsOpenPost] = useState(false);

  const gogo = useHistory();

  const [values, setValues] = useState({
    email: '',
    password: '',
    confirmPassword: '',
    phone: '',
    name: '',
    address: '',
  })

  const [guideTxts, setGuideTxts] = useState({
    userGuide: '최대 20자 까지 가능합니다.',
    emailGuide: '이메일 형식에 맞게 작성해 주세요.',
    pwdGuide: '숫자와 문자를 조합해서 최소 8글자는 입력해 주세요.',
    confirmPwdGuide: '한번더 입력해 주세요.',
    nameGuide: '',
    phoneGuide: '. 을 입력하지 말아 주세요.'
  });

  const [error, setError] = useState({
    userIdError: '',
    emailError: '',
    pwdError: '',
    confirmPwd: '',
    nameError: '',
    phoneError: ''
  })

  const isUserId = userId => {
    const userIdRegex = /^[a-z0-9_!@$%^&*-+=?"]{1,20}$/
    return userIdRegex.test(userId);
  }

  const isEmail = email => {
    const emailRegex = /^(([^<>()\].,;:\s@"]+(\.[^<>()\].,;:\s@"]+)*)|(".+"))@(([^<>()¥[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;

    return emailRegex.test(email);
  };

  const isPwd = pass => {
    const pwdRegex = /^.*(?=.{6,40})(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@$!%*#?&-]).*$/;
    return pwdRegex.test(pass);
  }

  const isPhone = phone => {
    const phoneRegex = /^[0-9\b -]{0,13}$/;
    return phoneRegex.test(phone)
  }

  const confirmPassword = (pass, confirmPass) => {
    return pass === confirmPass
  }

  const onTextCheck = () => {
    let userIdError = "";
    let emailError = "";
    let pwdError = "";
    let confirmPwd = "";
    let nameError = "";
    let phoneError = "";


    if (!isUserId(values.userId)) userIdError = "아이디 형식을 확인 해 주세요.( 한글 불가 )";
    if (!isEmail(values.email)) emailError = "email 형식이 아닙니다.";
    if (!isPwd(values.password)) pwdError = "비밀번호 조건을 만족 할 수 없습니다.";
    if (!confirmPassword(values.password, values.confirmPassword)) confirmPwd = "비밀번호가 일치하지 않습니다.";
    if (values.userId === values.password) pwdError = "아이디를 비밀번호로 사용 할 수 없습니다.";
    if (!isPhone(values.phone)) phoneError = "휴대폰 형식이 아닙니다.";

    if (values.name.length === 0) nameError = "이름을 입력해주세요.";
    
    setError({
      userIdError, emailError, pwdError, confirmPwd, nameError, phoneError
    })

    if (userIdError || emailError || pwdError || confirmPwd || nameError || phoneError) return false;
    return true;
  }

  const handleChangeForm = (e) => {
    setValues({
      ...values,
      [e.target.name]: e.target.value
    });
  }

  const checkEmail = () => {
    fetch(`/user-service/users/checkemail`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: values.email
      }),
    }).
      then(res => res.text()).
      then(res => {
        if (res === "true") {
          alert("이미 등록된 계정입니다.")
        }
        else {
          alert("사용 가능한 이메일입니다.");
        }
      })
  }

  const handlePutUserLists = (e) => {

    e.preventDefault();

    const valid = onTextCheck();

    if (!valid) console.error("retry");
    else {
      fetch(`/user-service/users`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: values.email,
          password: values.password,
          confirmPassword: values.confirmPassword,
          phoneNum: values.phone,
          applyName: values.name,
          address: values.address
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
            <div class="col-lg-4 mx-auto">
              <div class="auth-form-light text-left py-5 px-4 px-sm-5">
                <Brand />
                <h4>회원가입</h4>
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
                      placeholder="이메일 주소" />
                  </div>
                  <button class="btn btn-outline-success" style={{marginBottom:"1rem", marginTop:"-1rem",padding:"0.7rem", float:"right"}} type="button" onClick={checkEmail}>이메일 중복 확인</button>
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
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="phone-number"
                      name="phone"
                      value={values.phone}
                      onChange={handleChangeForm}
                      placeholder="휴대폰 번호" />
                  </div>
                  {/* <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                    Launch demo modal
                  </button>

                  <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <DaumPost data={address}/>
                        </div>
                      </div>
                    </div>
                  </div> */}
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="address"
                      name="address"
                      value={values.address}
                      onChange={handleChangeForm}
                      placeholder="주소" />
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
                    <button type="submit" class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">SIGN UP</button>
                  </div>
                  <div class="text-center mt-4 font-weight-light">
                    Already have an account? <Link to="/login">Login</Link>
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