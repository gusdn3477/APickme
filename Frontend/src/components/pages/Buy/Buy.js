import Header from '../../layout/Header';
import Footer from '../../layout/Footer';
import Bread from '../../elements/ui/Bread';
import BuyForm from './BuyForm'
import { Fragment } from 'react';
import { useParams } from 'react-router';

export default function Buy(){

    const {id} = useParams(); // 이렇게 사용해야 됨
    return(
        <Fragment>
            <Header/>
            <Bread breadName ="주문하기" id={id} />
            <BuyForm id={id}/>
            <Footer/>
        </Fragment>
    );
}