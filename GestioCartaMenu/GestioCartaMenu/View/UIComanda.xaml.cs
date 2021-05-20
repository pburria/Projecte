using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using MySQL;

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace GestioCartaMenu.View
{
    public sealed partial class UIComanda : UserControl
    {
        public UIComanda()
        {
            this.InitializeComponent();
        }
        public Comanda Comanda
        {
            get { return (Comanda)GetValue(ComandaProperty); }
            set { SetValue(ComandaProperty, value); }
        }

        // Using a DependencyProperty as the backing store for Comanda.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty ComandaProperty =
            DependencyProperty.Register("Comanda", typeof(Comanda), typeof(UIComanda), new PropertyMetadata(null));

        private void ucComanda_Loaded(object sender, RoutedEventArgs e)
        {
            txbData.Text = Comanda.Data.ToString("dd/MM/yyyy");
            ordenarIMostrarLlista( LiniaComanda.getLiniaComandaPerComanda(Comanda.Codi));

        }

        private void ordenarIMostrarLlista(List<LiniaComanda> llista)
        {
            llista.Sort(delegate (LiniaComanda x, LiniaComanda y) {
                return x.Acabat.CompareTo(y.Acabat);
            });
            lsvLiniaComanda.ItemsSource = llista;
        }

        private void lsvLiniaComanda_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (lsvLiniaComanda.SelectedValue != null)
            {
                long t = LiniaComanda.GetNumeroPlatsNoAcabats(Comanda.Codi);
                if (t == 1)
                {
                    LiniaComanda l = (LiniaComanda)lsvLiniaComanda.SelectedValue;
                    if (l.Acabat == false)
                    {
                        mostrarDialogeInserirUltimPlatAcabat("Al clicar aquest plat significa que la comanda ja esta finalitzada, vols continuar?", l);
                    }
                }
                else
                {
                    LiniaComanda l = (LiniaComanda)lsvLiniaComanda.SelectedValue;
                    l.Acabat = true;
                    LiniaComanda.UpdatePlatAcabat(l.Plat,Comanda.Codi);
                }
            }

        }

        private async void mostrarDialogeInserirUltimPlatAcabat(string txt, LiniaComanda l)
        {
            ContentDialog cd = new ContentDialog()
            {
                Content = txt,
                PrimaryButtonText = "OK",
                CloseButtonText = "CLOSE"
            };
            ContentDialogResult cdr = await cd.ShowAsync();
            if (cdr.Equals(ContentDialogResult.Primary))
            {
                LiniaComanda.UpdatePlatAcabat(l.Plat, Comanda.Codi);
            }
        }

        private void lsvLiniaComanda_Tapped(object sender, TappedRoutedEventArgs e)
        {
            ordenarIMostrarLlista(LiniaComanda.getLiniaComandaPerComanda(Comanda.Codi));

        }
    }
}
