import React, { useState, useEffect } from 'react';
import { useHistory } from "react-router";
import { Link } from 'react-router-dom';
import Brand from '../../elements/widgets/brand/Brand';

export default function ProductMakeForm() {

  const gogo = useHistory();

  const [usersDatas, setUsersDatas] = useState([]);

  const [values, setValues] = useState({
    productName: '',
    productId: '',
    unitPrice: '',
    image: '',
    writer: '',
    stock: '',
  })

  const [guideTxts, setGuideTxts] = useState({
    productName: '최대 20자 까지 가능합니다.',
    productId: '최대 20자까지 가능합니다',
    stock: '숫자로 입력해 주세요.',
    unitPrice: '숫자로 입력해 주세요.',
    image: '',
    writer: '최대 10자까지 입력 가능합니다.'
  });

  const [error, setError] = useState({
    productName: '',
    productId: '최대 20자까지 가능합니다',
    stock: '',
    unitPrice: '',
    image: '',
    writer: ''
  })


  const onTextCheck = () => {
    let productNameError = "";
    let productIdError = "";
    let stockError = "";
    let unitPriceError = "";
    let writerError = "";

    setError({
      productIdError, productNameError, stockError, unitPriceError, writerError
    })

    if (productIdError || productNameError || stockError || unitPriceError || writerError) return false;
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
      fetch(`/catalog-service/catalogs`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          productId: values.productId,
          productName: values.productName,
          stock: values.stock,
          unitPrice: values.unitPrice,
          image: values.image,
          writer: values.writer,
        }),
      })
        .then(res => res.json())
        .then((res) => {
          gogo.push("/");
        })
    }
  }

  const isSelectedImg = (e) => {
    if (e.target.files != null) {
      const fd = new FormData();
      fd.append('filename', e.target.files[0]);
      fetch('/catalog-service/carts/image', {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          fd
        }).then(res => { })
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
                <Brand/>
                <h4>새로운 인사담당자를 등록해 보세요.</h4>
                <form class="pt-3">
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="exampleInputUsername1" placeholder="이름" />
                  </div>
                  <div class="form-group">
                    <input type="email" class="form-control form-control-lg" id="exampleInputEmail1" placeholder="이메일" />
                  </div>
                  <div class="form-group">
                    <select class="form-control form-control-lg" id="exampleFormControlSelect2">
                      <option>회사 코드</option>
                      {/* <option>Country</option> */}
                      <option>1001</option>
                      <option>1002</option>
                      <option>1003</option>
                      <option>1004</option>
                      <option>1005</option>
                    </select>
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="비밀번호" />
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-lg" id="exampleInputPassword2" placeholder="비밀번호 확인" />
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
                    <a class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn" href="../../index.html">등록하기</a>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    // <div className="myaccount-area pb-80 pt-100">
    //     <div className="container">
    //         <div className="row">
    //             <div className="ml-auto mr-auto col-lg-9">
    //                 <div className="myaccount-wrapper">
    //                     <div className="accordion" id="accordionPanelsStayOpenExample">
    //                         <div className="accordion-item single-my-account mb-20 card">
    //                             <div className="panel-heading card-header" id="panelsStayOpen-headingOne">
    //                                 <button className="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true" aria-controls="panelsStayOpen-collapseOne">
    //                                     <h3 className="panel-title">상품 등록</h3>
    //                                 </button>
    //                             </div>
    //                             <div id="panelsStayOpen-collapseOne" className="accordion-collapse collapse show" aria-labelledby="panelsStayOpen-headingOne">
    //                             <div className="card-body">
    //     <div className="myaccount-info-wrapper">
    //     <form  onSubmit={handlePutUserLists}>
    //         <div className="account-info-wrapper">
    //             <h4>등록할 상품의 정보를 입력해 주세요.</h4>
    //         </div>
    //         <div className="row">

    //             <div className="col-lg-12 col-md-12">
    //                 <div className="billing-info">
    //                     <label>상품 제목</label>
    //                     <input 
    //                         type="text"
    //                         name="productName"
    //                         value={values.productName}
    //                         onChange={handleChangeForm}
    //                         placeholder="상품 제목을 입력해 주세요."
    //                     />
    //                 </div>
    //             </div>

    //             <div className="col-lg-12 col-md-12">
    //                 <div className="billing-info">
    //                     <label>ISBN</label>
    //                     <input 
    //                         type="text"
    //                         name="productId"
    //                         value={values.productId}
    //                         onChange={handleChangeForm}
    //                         placeholder="ISBN을 입력해 주세요."
    //                     />
    //                 </div>
    //             </div>

    //             <div className="col-lg-12 col-md-12">
    //                 <div className="billing-info">
    //                     <label>재고</label>
    //                     <input 
    //                         type="text"
    //                         name="stock"
    //                         value={values.stock}
    //                         onChange={handleChangeForm}
    //                         placeholder="재고를 입력해 주세요."
    //                     />
    //                 </div>
    //             </div>

    //             <div className="col-lg-12 col-md-12">
    //                 <div className="billing-info">
    //                     <label>가격</label>
    //                     <input 
    //                         type="text"
    //                         name="unitPrice"
    //                         value={values.unitPrice}
    //                         onChange={handleChangeForm}
    //                         placeholder="한 권당 가격을 입력해 주세요."
    //                     />
    //                 </div>
    //             </div>
    //             <div className="col-lg-12 col-md-12">
    //                 <div className="billing-info">
    //                     <label>작가</label>
    //                     <input 
    //                         type="text"
    //                         name="writer"
    //                         value={values.writer}
    //                         onChange={handleChangeForm}
    //                         placeholder="작가 이름을 입력해 주세요."
    //                     />
    //                 </div>
    //             </div>
    //             <div className="col-lg-12 col-md-12">
    //                 <div className="billing-info">
    //                     <label>이미지</label> {/* 타입 변경 요먕 */}
    //                     <input 
    //                         type="file"
    //                         accept="image/jpeg, image/jpg"
    //                         name="image"
    //                         value={values.image}
    //                         onChange={handleChangeForm}
    //                         placeholder="이미지를 등록해 주세요(선택)"
    //                     />
    //                 </div>
    //             </div>

    //         </div>

    //         <div className="billing-back-btn">
    //             <div className="billing-btn">
    //                 <button type="submit">등록</button>
    //             </div>
    //         </div>
    //         </form>
    //     </div>
    // </div>
    //                             </div>
    //                         </div>
    //                     </div>
    //                 </div>
    //             </div>
    //         </div>
    //     </div>
    // </div>
  );

}
