import Header from '../../layout/Header';
import Footer from '../../layout/Footer';
import Bread from '../../elements/ui/Bread';
import SearchForm from '../Search/SearchForm';
import { Fragment } from 'react';


export default function Register(){

    return(
        <Fragment>
            <Header/>
            <Bread breadName ="상품 검색" />
            <SearchForm />
            <Footer/>
        </Fragment>
    );
}