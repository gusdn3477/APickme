import React, { Component, useRef } from 'react';
import DaumPostCode from 'react-daum-postcode';
//import './AddressModal.scss'
class AddressModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            phone: "",
            address: "",
            zoneCode : "",
            fullAddress : "",
            isDaumPost : false,
            isRegister : false,
            register: [],
        }
    }

    handleOpenPost = () => {
        this.setState({
            isDaumPost : true
        })
    }

    onClose = (state) => {
        if(state === 'FORCE_CLOSE'){
            alert('정상 종료');
        }
        else{
            alert('정상 종료 아님');
        }
    }

    handleOffPost = () => {
      this.setState({
          isDaumPost : false
      })
  }

    // postcode
    handleAddress = (data) => {
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
        this.setState ({
            fullAddress: AllAddress,
            zoneCode : zoneCodes
        })
      }

    render() {
        const { isModalShow, isModalClose } = this.props;
        const { name, phone, address, isDaumPost, fullAddress, zoneCode, isRegister } = this.state;
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
                          <button type="button" class="btn btn-primary" onClick={this.handleOpenPost}>
                              <span>우편번호 찾기</span>
                          </button>
                          <div className="zipCode">{zoneCode}</div>
                      </div>
                      {
                        isDaumPost ? 
                        <div>
                              <DaumPostCode
                                  onComplete={this.handleAddress}
                                //   onClose={this.onClose}
                                //   autoClose
                                  width={width}
                                  height={height}
                                  style={modalStyle}
                                  isDaumPost={isDaumPost}
                          		/>
                              
                          <button type="button" style={closeBtnStyle} class="btn btn-primary" onClick={this.handleOffPost}>
                              <span>닫기</span>
                          </button>
                          </div>
                          : null
                      }
                      <div className="address" value={fullAddress} name="fullAdress">{fullAddress}</div>
                      <div className="addressBox">
                          <input type="text" name="address" onChange={this.handleInput}/>
                      </div>
                  </div>
              </div>
        );
    }
}

export default AddressModal;