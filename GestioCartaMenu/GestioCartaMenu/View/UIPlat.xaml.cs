using MySQL;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Threading.Tasks;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Storage.Streams;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace GestioCartaMenu.View
{
    public sealed partial class UIPlat : UserControl
    {
        public UIPlat()
        {
            this.InitializeComponent();
        }

        public Plat Plat
        {
            get { return (Plat)GetValue(PlatProperty); }
            set { SetValue(PlatProperty, value); }
        }

        // Using a DependencyProperty as the backing store for Plat.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty PlatProperty =
            DependencyProperty.Register("Plat", typeof(Plat), typeof(UIPlat), new PropertyMetadata(null));

        private async void Mostrar()
        {
            using (InMemoryRandomAccessStream stream = new InMemoryRandomAccessStream())
            {
                using (DataWriter writer = new DataWriter(stream.GetOutputStreamAt(0)))
                {
                    writer.WriteBytes(Plat.Image);
                    await writer.StoreAsync();
                }
                var image = new BitmapImage();
                await image.SetSourceAsync(stream);

                foto.Source = image;
            }
        }

        private void ucPlat_Loaded(object sender, RoutedEventArgs e)
        {
            Mostrar();
            if (Plat.Disponible)
            {
                disponible.Text = "Disponible";
            }
            else
            {
                disponible.Text = "No Disponible";

            }
            preu.Text = preu.Text + "€";
        }
    }
}
