import React, {useState, useEffect} from 'react';
import { useHistory } from "react-router";

export default function RegisterForm() {

    const { kakao } = window;
    useEffect(() => {
        let mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = {
            center: new kakao.maps.LatLng(37.37935884087194, 127.11676689820835), // 지도의 중심좌표
            level: 4 // 지도의 확대 레벨
        };

        // 마커를 생성합니다
        let marker = new kakao.maps.LatLng(37.37935884087194, 127.11676689820835);
        marker = new kakao.maps.Marker({
            position: marker
        })
        const map = new kakao.maps.Map(mapContainer, mapOption);
        marker.setMap(map);
        //위도, 경도로 변환 및 마커표시

    }, [/*표시할 주소 변수*/]);

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
                                            <h3 className="panel-title">찾아오시는 길</h3>
                                        </button>
                                    </div>
                                    <div id='map' style={{
                                        width: '100%',
                                        height: '300px'}}>
                                    </div>
                                    <div id="panelsStayOpen-collapseOne" className="accordion-collapse collapse show" aria-labelledby="panelsStayOpen-headingOne">
                                    <div className="card-body">
                                    <div className="myaccount-info-wrapper">
                                    <h4>경기도 성남시 분당구 황새울로258번길 29,  티맥스수내타워 8-9층 티맥스소프트</h4>
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