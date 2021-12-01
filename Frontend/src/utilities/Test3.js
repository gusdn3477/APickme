import React, { useRef, useState } from 'react';
import DaumPostCode from 'react-daum-postcode';
//import './AddressModal.scss'

export default function Test3({setVal}) {

    const [values, setValues] = useState({
        name: "",
        phone: "",
        address: "",
        zoneCode : "",
        fullAddress : "",
        isDaumPost : false,
        isRegister : false,
        register: []
    })

    const handleOpenPost = () => {
        setValues({isDaumPost:true})
        // setValues({ 
        //     ...values, 
        //     [e.target.isDaumPost]: true
        // });
        // setDaumPost(true);
    }

    const handleOffPost = () => {
        setValues({isDaumPost:false})
        // setValues({ 
        //     ...values, 
        //     [e.target.isDaumPost]: false
        // });
    }

    // postcode
    const handleAddress = (data) => {
        let AllAddress = data.address;
        let extraAddress = ''; 
        let zoneCodes = data.zonecode;
        
        if (data.addressType === 'R') {
          if (data.bname !== '') {
            extraAddress += data.bname;
          }
          if (data.buildingName !== '') {
            extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
          }
          AllAddress += (extraAddress !== '' ? ` (${extraAddress})` : '');
        }
        setValues({fullAddress:AllAddress, zoneCode:zoneCodes});
    }

    const { name, phone, address, isDaumPost, fullAddress, zoneCode, isRegister } = useState();
        // DaumPostCode style
        const width = 595;
        const height = 450;
        const closeBtnStyle = {
          position: "absolute",
            top: 330,
            left: "687px",
            zIndex: "100",
            border: "1px solid #000000",
            overflow: "hidden"
        }
        const modalStyle = {
            position: "absolute",
            top: 370,
            left: "150px",
            zIndex: "100",
            border: "1px solid #000000",
            overflow: "hidden"
        }

        return (
              <div className="modalRow">
                  <div className="modalCell cellTit">
                      <div>
                          <span><b>*</b>주소</span>
                      </div>
                  </div>
                  <div className="modalCell">
                      <div className="cellFirst">
                          <button type="button" class="btn btn-primary" onClick={handleOpenPost}>
                              <span>우편번호 찾기</span>
                          </button>
                          <div className="zipCode">{zoneCode}</div>
                      </div>
                      {
                        values.isDaumPost ? 
                        <div>
                              <DaumPostCode
                                  onComplete={handleAddress}
                                //   onClose={this.onClose}
                                //   autoClose
                                  width={width}
                                  height={height}
                                  style={modalStyle}
                                  isDaumPost={isDaumPost}
                          		/>
                              
                          <button type="button" style={closeBtnStyle} class="btn btn-primary" onClick={handleOffPost}>
                              <span>닫기</span>
                          </button>
                          </div>
                          : null
                      }
                      <div className="address" value={fullAddress} name="fullAdress">{fullAddress}</div>
                      <div className="addressBox">
                          <input type="text" name="address" />
                      </div>
                  </div>
              </div>
        );
    }
