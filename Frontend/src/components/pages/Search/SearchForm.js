import Postcode from '@actbase/react-daum-postcode';
import React, {useState, useEffect} from 'react';
import { useHistory } from "react-router";
import SearchFormView from './SearchFormView';

export default function SearchForm() {

    const gogo = useHistory();

    const [usersDatas, setUsersDatas] = useState([]); //책 데이터입니다.

    const [values, setValues] = useState({
        ISBN : '',
        productName : '',
        stock : '',
        startDate : '',
        endDate : '',
        unitPrice : '',
        createdAt : '',
        image : '',
        writer : ''
    })

    const [guideTxts, setGuideTxts] = useState({
        ISBN : '',
        productName : '',
        stock : '',
        unitPrice : '',
        startDate : '',
        endDate : '',
        createdAt : '',
        image : '',
        writer : ''
    });

    const [error, setError] = useState({
        ISBN : '',
        productName : '',
        stock : '',
        unitPrice : '',
        startDate : '',
        endDate : '',
        createdAt : '',
        image : '',
        writer : ''
      })

/*
      const onTextCheck = () => {
        let ISBNError = "";
        let productNameError = "";
        let stcokError = "";
        let unitPriceError = "";
        let startDateError = "";
        let endDateError = "";
        
        if (values.productName.length === 0) productNameError = "이름을 입력해주세요.";
    
        //console.log(userIdError, emailError, pwdError, confirmPwd, nameError, phoneError, userTypesError, useConfirmError)
        setError({
          userIdError, nameError
        })
    
        if (userIdError || nameError ) return false;
        return true;
      }
*/
    const handleChangeForm = (e) => {
        setValues({ 
            ...values, 
            [e.target.name]: e.target.value 
        });
    }

    const searchByProductName = (e) => {
        //alert(usersDatas.length);
        //console.log(values);
        e.preventDefault();

        //const valid = onTextCheck(); 테스트를 위하여 잠시 주석처리.
        if(values.productName.length <= 1){
            console.error("retry");
            alert("두 글자 이상 입력해 주세요");
        }
        else {
            fetch(`/catalog-service/catalogs/search/productname/${values.productName}`,{
                headers: {
                    "Content-Type": "application/json",
                }
            }).
            then(res => {
                return res.json()
            })
            .then(data => setUsersDatas(data));
            }
    }

    const searchByWriter = (e) => {
        //alert(usersDatas.length);
        //console.log(values);
        e.preventDefault();

        //const valid = onTextCheck(); 테스트를 위하여 잠시 주석처리.
        if(values.writer.length <= 1){
            console.error("retry");
            alert("두 글자 이상 입력해 주세요");
        }
        else {
            fetch(`/catalog-service/catalogs/search/writer/${values.writer}`,{
                headers: {
                    "Content-Type": "application/json",
                }
            }).
            then(res => {
                return res.json()
            })
            .then(data => setUsersDatas(data));
            }
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
                                            <h3 className="panel-title">상품 검색</h3>
                                        </button>
                                    </div>
                                    <div id="panelsStayOpen-collapseOne" className="accordion-collapse collapse show" aria-labelledby="panelsStayOpen-headingOne">
                                    <div className="card-body">
            <div className="myaccount-info-wrapper">
            <form  onSubmit={searchByProductName}>
                <div className="row">
                    
                    <div className="col-lg-12 col-md-12">
                        <div className="billing-info">
                            <label>제목으로 검색</label>
                            <input 
                                type="text"
                                name="productName"
                                value={values.productName}
                                onChange={handleChangeForm}
                                // placeholder="ID를 입력해 주세요."
                            />
                        </div>
                    </div>
                    {
                        error.userIdError 
                            ? 
                                <div style={{ color: "red", fontSize: "10px", margin: '-5px 0 10px 15px' }}>{error.userIdError}</div>
                            :
                                <div style={{ color: "gray", fontSize: "10px", margin: '-5px 0 10px 15px' }}>{guideTxts.userGuide}</div>
                    }
                    
                    {/*}
                    <div className="col-lg-12 col-md-12">
                        <div className="billing-info">
                            <label>날짜로 검색(2021-02-05 식으로 입력)</label>
                            <div>검색 시작 날짜</div>
                            <input 
                                type="text"
                                name="startDate"
                                value={values.startDate}
                                onChange={handleChangeForm}
                            />
                            <div>검색 종료 날짜</div>
                            <input 
                                type="text"
                                name="endDate"
                                value={values.endDate}
                                onChange={handleChangeForm}
                            />
                        </div>
                    </div>
                    {
                        error.nameError 
                            ? 
                                <div style={{ color: "red", fontSize: "10px", margin: '-5px 0 10px 15px' }}>{error.nameError}</div>
                            :
                                <div style={{ color: "gray", fontSize: "10px", margin: '-5px 0 10px 15px' }}>{guideTxts.nameGuide}</div>
                    }
                    */}
                </div>
                
                <div className="billing-back-btn">
                    <div className="billing-btn">
                        <button type="submit">검색하기</button>
                    </div>
                </div>
                </form>
                <form  onSubmit={searchByWriter}>
                <div className="row">
                    <div className="col-lg-12 col-md-12">
                        <div className="billing-info">
                            <label>작가 이름으로 검색</label>
                            <input 
                                type="text"
                                name="writer"
                                value={values.writer}
                                onChange={handleChangeForm}
                            />
                        </div>
                    </div>
                    {
                        error.nameError 
                            ? 
                                <div style={{ color: "red", fontSize: "10px", margin: '-5px 0 10px 15px' }}>{error.nameError}</div>
                            :
                                <div style={{ color: "gray", fontSize: "10px", margin: '-5px 0 10px 15px' }}>{guideTxts.nameGuide}</div>
                    }
                    
                </div>
                
                <div className="billing-back-btn">
                    <div className="billing-btn">
                        <button type="submit">검색하기</button>
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
            
            <div className="cart-main-area pt-90 pb-100">
            <div className="container">
                <div className="row">
                    {usersDatas.length > 0 ? <h3 className="cart-page-title">검색 결과</h3> : ""}
                    <div className="col-12">
                        <div className="table-content table-responsive cart-table-content">
                            <table>
                                <tbody>
                                {usersDatas && usersDatas.map((item, idx) => (
                                    <table>
                                    <thead>
                                        <tr>
                                            <th>Image</th>
                                            <th>Product Id</th>
                                            <th>Product Name</th>
                                            <th>STOCK</th>
                                            <th>Unit Price</th>
                                            <th>writer</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {
                                            <SearchFormView 
                                            key = {idx}
                                            data = {item}
                                            setUsersDatas = {setUsersDatas}
                                        />
                                        }
                                    </tbody>
                                </table>
                
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        </div>

        
    );

}