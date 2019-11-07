import React from 'react';
import { useTranslation } from 'react-i18next';
import '../dist/css/ChangeLenguage.css';
import argFlag from '../dist/icons/argentinianFlagHeart.png';
import usaFlag from '../dist/icons/usaFlag.png';

export default function ChangeLenguage() {
  const { i18n } = useTranslation();

  const changeLanguage = (lng) => {
    if (lng !== i18n.language) {
      i18n.changeLanguage(lng);
    }
  };

  return (
    <div className="col text-right">
      <img className="flag-arg" src={argFlag} alt="Argentinian Flag" onClick={() => changeLanguage('es')} role="presentation" />
      <img className="flag-usa" src={usaFlag} alt="USA flag" onClick={() => changeLanguage('en')} role="presentation" />
    </div>
  );
}
