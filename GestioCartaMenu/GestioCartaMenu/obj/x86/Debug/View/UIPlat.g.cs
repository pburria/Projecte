﻿#pragma checksum "D:\cromp\2n DAM\Projecte\GestioCartaMenu\GestioCartaMenu\View\UIPlat.xaml" "{406ea660-64cf-4c82-b6f0-42d48172a799}" "2C6641C564E15CC1470F9F3A43FC7BA4"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace GestioCartaMenu.View
{
    partial class UIPlat : 
        global::Windows.UI.Xaml.Controls.UserControl, 
        global::Windows.UI.Xaml.Markup.IComponentConnector,
        global::Windows.UI.Xaml.Markup.IComponentConnector2
    {
        /// <summary>
        /// Connect()
        /// </summary>
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 10.0.18362.1")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public void Connect(int connectionId, object target)
        {
            switch(connectionId)
            {
            case 1: // View\UIPlat.xaml line 1
                {
                    this.ucPlat = (global::Windows.UI.Xaml.Controls.UserControl)(target);
                    ((global::Windows.UI.Xaml.Controls.UserControl)this.ucPlat).Loaded += this.ucPlat_Loaded;
                }
                break;
            case 2: // View\UIPlat.xaml line 17
                {
                    this.foto = (global::Windows.UI.Xaml.Controls.Image)(target);
                }
                break;
            case 3: // View\UIPlat.xaml line 34
                {
                    this.disponible = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 4: // View\UIPlat.xaml line 30
                {
                    this.preu = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            default:
                break;
            }
            this._contentLoaded = true;
        }

        /// <summary>
        /// GetBindingConnector(int connectionId, object target)
        /// </summary>
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 10.0.18362.1")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public global::Windows.UI.Xaml.Markup.IComponentConnector GetBindingConnector(int connectionId, object target)
        {
            global::Windows.UI.Xaml.Markup.IComponentConnector returnValue = null;
            return returnValue;
        }
    }
}
