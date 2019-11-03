import React from 'react';
import { useTranslation } from 'react-i18next';
import '../dist/css/ChangeLenguage.css';
import argFlag from '../dist/icons/argentinianFlagHeart.png';
import usaFlag from '../dist/icons/usaFlag.png';

export default function ChangeLenguage() {
  const { i18n } = useTranslation();

  const changeLanguage = (lng) => {
    i18n.changeLanguage(lng);
  };
  return (
    <div>
      <button type="button"><img className="flag" src={argFlag} alt="Argentinian Flag" onClick={() => changeLanguage('es')} /></button>
      <button type="button"><img className="flag" src={usaFlag} alt="USA flag" onClick={() => changeLanguage('en')} /></button>
    </div>
  );
}
