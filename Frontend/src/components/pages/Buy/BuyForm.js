import React, {useState, useEffect} from 'react';
import { useHistory, useParams } from "react-router";

export default function BuyForm({id}) {

    const gogo = useHistory();
    const [userValues, setUserValues] = useState({
        name: '',
        phone: '',
        address: ''
    })
    const [values, setValues] = useState({
        productId: '',
        productName: '',
        unitPrice: 0,
        qty: 0,
        image: '',
        writer: ''
    })

    const [error, setError] = useState({
        userIdError: '',
        emailError: '',
        pwdError: '',
        confirmPwd: '',
        nameError: '',
        phoneError: '',
        addressError : ''
      })

      const [guideTxts, setGuideTxts] = useState({
        userGuide : '최대 20자 까지 가능합니다.',
        nameGuide : '',
        addressGuide : '빈 칸을 입력하지 말아주세요',
        phoneGuide : '. 을 입력하지 말아 주세요.'
    });


    const handlePutOrderList = (e) => {

        e.preventDefault();
        const valid = onTextCheck();
        if (!valid) console.error("retry");

        else{
            fetch(`/order-service/${localStorage.getItem('userId')}/orders`,{
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    productId: values.productId,
                    qty : localStorage.getItem('qty'),
                    unitPrice : values.unitPrice
                }),
            }).
            then(
                alert("주문 성공!"),
                gogo.push('/')
            )
        }
    }

    useEffect(()=>{
        fetch(`/catalog-service/catalogs/${id}`)
        .then(res => {
            return res.json();
        })
        .then(data => {
            setValues(data);
            // console.log(data);
        });
    },[]);

    useEffect(()=>{
        fetch(`/user-service/users/${localStorage.getItem('userId')}`)
        .then(res => {
            return res.json();
        })
        .then(data => {
            setUserValues(data);
            console.log(data);
        });
    },[]);

    const handleChangeForm = (e) => {
        setUserValues({ 
            ...userValues, 
            [e.target.name]: e.target.value 
        });
    }

  const isUserId = userId => {
    const userIdRegex = /^[a-z0-9_!@$%^&*-+=?"]{1,20}$/
    return userIdRegex.test(userId);
  }
  
  const isPhone = phone => {
    const phoneRegex = /^[0-9\b -]{0,13}$/;
    return phoneRegex.test(phone)
  }

      const onTextCheck = () => {
        let userIdError = "";
        let nameError = "";
        let phoneError = "";
        let addressError = "";
        
    
        if (!isUserId(values.userId)) userIdError = "아이디 형식을 확인 해 주세요.( 한글 불가 )"; 
        // if (!isPhone(values.phone)) phoneError = "휴대폰 형식이 아닙니다.";
        if ((userValues.name).length === 0) nameError = "이름을 입력해 주세요.";
        if ((userValues.address).length === 0) addressError = "주소를 입력해 주세요.";
    
        setError({
          userIdError, nameError, phoneError, addressError
        })
    
        if (userIdError || nameError || phoneError || addressError ) return false;
        return true;
      }

    return(
        <div className="myaccount-area pb-80 pt-100">
            <div className="container">
                <div className="row">
                    <div className="ml-auto mr-auto col-lg-9">
                        <div className="myaccount-wrapper">
                            <div className="accordion" id="accordionPanelsStayOpenExample">
                                <div className="accordion-item single-my-account mb-20 card">
                                    <div className="panel-heading card-header" id="panelsStayOpen-headingOne">
                                        <button className="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true" aria-controls="panelsStayOpen-collapseOne">
                                            <h3 className="panel-title">주문하기</h3>
                                        </button>
                                    </div>
                                    <div id="panelsStayOpen-collapseOne" className="accordion-collapse collapse show" aria-labelledby="panelsStayOpen-headingOne">
                                    <div className="card-body">
                                        <div className="myaccount-info-wrapper">
                                        <form  onSubmit={handlePutOrderList}>
                                            <div className="row">
                                                <div className="col-lg-12 col-md-12">
                                                    <div className="billing-info">
                                                        <label>상품Id</label>
                                                        <input 
                                                            type="text"
                                                            name="productId"
                                                            value={values.productId}
                                                            readOnly
                                                            // placeholder="ID를 입력해 주세요."
                                                        />
                                                    </div>
                                                </div>
                                                
                                                <div className="col-lg-12 col-md-12">
                                                    <div className="billing-info">
                                                        <label>상품이름</label>
                                                        <input 
                                                            type="text"
                                                            name="productName"
                                                            value={values.productName}
                                                            readOnly
                                                        />
                                                    </div>
                                                </div>
                                                <div className="col-lg-12 col-md-12">
                                                    <div className="billing-info">
                                                        <label>단위 가격</label>
                                                        <input 
                                                            type="text"
                                                            name="unitPrice"
                                                            value={values.unitPrice}
                                                            readOnly
                                                        />
                                                    </div>
                                                </div>
                                                <div className="col-lg-12 col-md-12">
                                                    <div className="billing-info">
                                                        <label>구매 갯수</label>
                                                        <input 
                                                            type="text"
                                                            name="qty"
                                                            value={localStorage.getItem('qty')}
                                                            readOnly
                                                        />
                                                    </div>
                                                </div>
                                                <div className="col-lg-12 col-md-12">
                                                    <div className="billing-info">
                                                        <label>총 가격</label>
                                                        <input 
                                                            type="text"
                                                            name="totalPrice"
                                                            value={values.unitPrice * localStorage.getItem('qty')}
                                                        />
                                                    </div>
                                                </div>
                                                <div className="col-lg-12 col-md-12">
                                                    <div className="billing-info">
                                                        <label>이름</label>
                                                        <input 
                                                            type="text"
                                                            name="name"
                                                            value={userValues.name}
                                                            readOnly
                                                        />
                                                    </div>
                                                </div>
                                                <div className="col-lg-12 col-md-12">
                                                    <div className="billing-info">
                                                        <label>연락처</label>
                                                        <input 
                                                            type="text"
                                                            name="phone"
                                                            value={userValues.phone}
                                                            readOnly
                                                        />
                                                    </div>
                                                </div>
                                                {
                                                    error.phoneError 
                                                        ? 
                                                    <div style={{ color: "red", fontSize: "10px", margin: '-5px 0 10px 15px' }}>{error.phoneError}</div>
                                                        :
                                                    <div style={{ color: "gray", fontSize: "10px", margin: '-5px 0 10px 15px' }}>{guideTxts.phoneGuide}</div>
                                                }
                                                <div className="col-lg-12 col-md-12">
                                                    <div className="billing-info">
                                                        <label>주소</label>
                                                        <input 
                                                            type="text"
                                                            name="address"
                                                            value={userValues.address}
                                                            onChange={handleChangeForm}
                                                        />
                                                    </div>
                                                </div>
                                                {
                                                    error.addressError 
                                                        ? 
                                                    <div style={{ color: "red", fontSize: "10px", margin: '-5px 0 10px 15px' }}>{error.addressError}</div>
                                                        :
                                                    <div style={{ color: "gray", fontSize: "10px", margin: '-5px 0 10px 15px' }}>{guideTxts.addressGuide}</div>
                                                }
                                            </div>
                                            
                                            <div className="billing-back-btn">
                                                <div className="billing-btn">
                                                    <button type="submit">구매</button>
                                                </div>
                                            </div>
                                            </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

}