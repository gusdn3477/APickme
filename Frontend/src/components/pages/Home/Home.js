import React from "react";
import Banner from "../../elements/ui/Banner";
import Header from "../../layout/Header";
import Footer from "../../layout/Footer";
import Welcome from "../../elements/widgets/Home/Welcome";
import { useHistory, Redirect } from "react-router";
import { useState } from "react";
import Calender from "../../elements/widgets/Home/Calender";

export default function Home() {
  const gogo = useHistory();
  const [loading, setLoading] = useState(true);

  if (localStorage.getItem("token")) {
    // 토큰이 있는 경우
    return (
      <div id="wrap">
        <Header />
        <div className="container-scroller">
          <div className="container-fluid page-body-wrapper">
            {/* Banner는 왼쪽에 있는 리스트 형식의 메뉴 */}
            <Banner />
            {/* 여기부터 프사 누르면 나오는 메뉴 */}
            <div className="main-panel">
              <div className="content-wrapper">
                <div className="row">
                  <div className="col-md-12 grid-margin">
                    <div className="row">
                      <Welcome />
                    </div>
                  </div>
                </div>

                <div className="row">
                  <Calender />
                </div>
              </div>
              <Footer />
            </div>
          </div>
        </div>
      </div>
    );
  } else {
      return <Redirect to="/login" />;
  }
}
