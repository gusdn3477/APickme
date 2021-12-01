import React, { useState } from 'react';
import PopupDom from './PopupDom';
import PopupPostCode from './PopupPostCode';
 
const Test = () => {

    // 모달창 상태 관리
    const [isModalOpen, setIsModalOpen] = useState(false)
 
	// 모달창 열기
    const openModal = () => {
        setIsModalOpen(true)
    }
 
	// 모달창 닫기
    const closeModal = () => {
        setIsModalOpen(false)
    }

	// 팝업창 상태 관리
    const [isPopupOpen, setIsPopupOpen] = useState(false)
 
	// 팝업창 열기
    const openPostCode = () => {
        setIsPopupOpen(true)
    }
 
	// 팝업창 닫기
    const closePostCode = () => {
        setIsPopupOpen(false)
    }
 
    return(
        <div>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" onClick={openPostCode}>
                우편번호 찾기
            </button>
                <div id='popupDom'>
                    {isPopupOpen && (
                        <PopupDom>
                            <PopupPostCode onClose={closePostCode} />
                        </PopupDom>
                    )}
                </div>
                
        </div>
    )
}
 
export default Test;