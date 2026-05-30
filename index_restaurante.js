/**
 * ============================================================
 *  BOT DE WHATSAPP — RESTAURANTe
 *  Tecnologías: whatsapp-web.js
 * ============================================================
 */

require('dotenv').config();

const { Client, LocalAuth, MessageMedia } = require('whatsapp-web.js');
const qrcode = require('qrcode-terminal');
const axios  = require('axios');

// ─── CONFIGURACIÓN ───────────────────────────────────────────
const CONFIG = {
  COMMAND_PREFIX: '!',
  // Agrega aquí tus variables de entorno o valores directos
};

// ─── CLIENTE DE WHATSAPP ─────────────────────────────────────
const client = new Client({
  authStrategy: new LocalAuth({ clientId: 'bot-restaurante' }),
  puppeteer: {
    executablePath: process.env.CHROME_PATH || 'C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe',
    args: ['--no-sandbox', '--disable-setuid-sandbox'],
  },
});

// ══════════════════════════════════════════════════════════════
//  EVENTOS DEL CLIENTE
// ══════════════════════════════════════════════════════════════

client.on('qr', async (qr) => {
  console.log('\n[Auth] 📱 Escanea el QR:\n');
  qrcode.generate(qr, { small: true });

  // También lo muestra en base64 para servidores
  const QRCode = require('qrcode');
  const qrDataUrl = await QRCode.toDataURL(qr);
  console.log('[Auth] 🔗 QR en base64:', qrDataUrl);
});

client.on('authenticated', () => {
  console.log('[Auth] ✅ Sesión autenticada correctamente.');
});

client.on('ready', () => {
  console.log('[Bot]  🚀 Bot listo y escuchando mensajes...');
});

client.on('disconnected', (reason) => {
  console.error('[Bot]  ❌ Cliente desconectado. Razón:', reason);
  process.exit(1);
});

// ══════════════════════════════════════════════════════════════
//  MANEJADOR PRINCIPAL DE MENSAJES
// ══════════════════════════════════════════════════════════════
client.on('message_create', async (msg) => {
  if (msg.fromMe) return;

  const cuerpo = (msg.body || '').trim();
  const chatId = msg.from;

  console.log(`[DEBUG] Mensaje: "${cuerpo}" | Chat: ${chatId}`);

  // Filtro de comandos
  if (!cuerpo.startsWith(CONFIG.COMMAND_PREFIX)) return;

  const partes  = cuerpo.slice(CONFIG.COMMAND_PREFIX.length).split(/\s+/);
  const comando = partes[0].toLowerCase();
  const args    = partes.slice(1);

  console.log(`[Comando] 📨 "${comando}" recibido en chat: ${chatId}`);

  switch (comando) {

    case 'menu':
      await cmdMenu(msg);
      break;

    case 'pedido':
      await cmdPedido(msg, args, chatId);
      break;

    case 'ayuda':
      await cmdAyuda(msg);
      break;

    default:
      break;
  }
});

// ══════════════════════════════════════════════════════════════
//  COMANDOS
// ══════════════════════════════════════════════════════════════

/**
 * !menu
 * Muestra el menú del restaurante.
 */
async function cmdMenu(msg) {
  const menu =
    `🍽️ *Menú del Restaurante*\n` +
    `─────────────────────────\n` +
    `🍔 Hamburguesa clásica — $80\n` +
    `🍕 Pizza personal — $95\n` +
    `🌮 Tacos (3 piezas) — $60\n` +
    `🥤 Refresco — $25\n` +
    `─────────────────────────\n` +
    `_Usa !pedido [platillo] para ordenar_`;

  await msg.reply(menu);
}

/**
 * !pedido [descripción]
 * Registra un pedido.
 */
async function cmdPedido(msg, args, chatId) {
  if (args.length === 0) {
    await msg.reply('❌ Debes especificar tu pedido.\nEjemplo: `!pedido Hamburguesa clásica y refresco`');
    return;
  }

  const pedido  = args.join(' ');
  const horaStr = new Date().toLocaleTimeString('es-MX', {
    hour    : '2-digit',
    minute  : '2-digit',
    timeZone: 'America/Mexico_City',
  });

  await msg.reply(
    `✅ *Pedido recibido*\n` +
    `─────────────────────────\n` +
    `🛒 ${pedido}\n` +
    `🕐 Hora: ${horaStr}\n` +
    `⏳ Tu pedido está siendo preparado.`
  );

  console.log(`[Pedido] 🛒 Nuevo pedido: "${pedido}" a las ${horaStr}`);
}

/**
 * !ayuda
 * Muestra los comandos disponibles.
 */
async function cmdAyuda(msg) {
  const ayuda =
    `🤖 *Bot Restaurante — Comandos*\n` +
    `─────────────────────────────────────\n` +
    `🍽️ *!menu* — Ver el menú completo\n` +
    `🛒 *!pedido [platillo]* — Hacer un pedido\n` +
    `❓ *!ayuda* — Muestra este mensaje`;

  await msg.reply(ayuda);
}

// ══════════════════════════════════════════════════════════════
//  MANEJO GLOBAL DE ERRORES
// ══════════════════════════════════════════════════════════════

process.om('uncaughtException', (err) => {
  console.error('[Process] ❌ Error no capturado:', err);
});

process.on('unhandledRejection', (reason) => {
  console.error('[Process] ❌ Promesa rechazada sin capturar:', reason);
});

// ──────────────────────────────────────────────────────────────
//  ARRANQUE DEL BOT
// ──────────────────────────────────────────────────────────────
console.log('[Bot]  🔧 Inicializando cliente de WhatsApp...');
client.initialize();
